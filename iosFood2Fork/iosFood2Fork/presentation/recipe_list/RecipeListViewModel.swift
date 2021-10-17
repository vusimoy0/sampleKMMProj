//
//  RecipeListViewModel.swift
//  iosFood2Fork
//
//  Created by Vusi Moyo on 2021/10/09.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

class RecipeListViewModel: ObservableObject {
    
    //dependencies
    let searchRecipes: SearchRecipes
    let foodCategoryUtil: FoodCategoryUtil
    
    //this annotation is what emits events
    @Published var state: RecipeListState = RecipeListState()
    
    @Published var showDialog: Bool = false
    
    init(searchRecipes: SearchRecipes,foodCategoryUtil: FoodCategoryUtil) {
        self.searchRecipes = searchRecipes
        self.foodCategoryUtil = foodCategoryUtil
        onTriggerEvent(stateEvent: RecipeListEvents.LoadRecipes())
    }
    
    func onTriggerEvent(stateEvent: RecipeListEvents) {
        
        switch stateEvent {
    
        case is RecipeListEvents.LoadRecipes:
            loadRecipes()
            
        case is RecipeListEvents.NewSearch:
            newSearch()
            
        case is RecipeListEvents.NextPage:
            nextPage()
            
        case is RecipeListEvents.OnUpdateQuery:
            onUpdateQuery(query: (stateEvent as! RecipeListEvents.OnUpdateQuery).query)
            
        case is RecipeListEvents.OnSelectCategory:
            onUpdateSelectedCategory(foodCategory: (stateEvent as! RecipeListEvents.OnSelectCategory).category)
            
        case is RecipeListEvents.OnRemoveHeadMessageFromQueue:
            removeHeadFromQueue()
            
        default:
            doNothing()
        }
    }
    
    private func nextPage() {
        let currentState = self.state.copy() as! RecipeListState
        updateState(page: Int(currentState.page) + 1)
        loadRecipes()
    }
    
    private func loadRecipes() {
        let currentState = (self.state.copy() as! RecipeListState)
        
        do{
            try searchRecipes.execute(page: Int32(currentState.page), query: currentState.query)
                .collectCommon(coroutineScope: nil, callback: { dataState in
                    if dataState != nil {
                        let data = dataState?.data
                        let message = dataState?.message
                        let loading = dataState?.isLoading ?? false
                        
                        self.updateState(isLoading: loading)
                        
                        if(data != nil) {
                            //what is this [Recipe] notation
                            self.appendRecipes(recipes: data as! [Recipe])
                        }
                        if(message != nil) {
                            //some error has occurred, so we need to build our generic message value
                            self.handleMessageByUIComponentType(message!)
                        }
                    }
                })
        }catch {
            //build an error
            //self.handleMessageByUIComponentType(message!)
            print("\(error)")
        }
    }
    
    private func newSearch() {
        resetSearchState()
        loadRecipes()
    }
    
    private func onUpdateQuery(query: String) {
        updateState(query: query)
    }
    
    private func onUpdateBottomRecipe(recipe: Recipe) {
        updateState(bottomRecipe: recipe)
    }
    
    private func appendRecipes(recipes: [Recipe]) {
        var currentState = (self.state.copy()) as! RecipeListState
        var currentRecipes = currentState.recipes
        currentRecipes.append(contentsOf: recipes)
        self.state = self.state.doCopy(isLoading: currentState.isLoading,
                                       page: currentState.page,
                                       query: currentState.query,
                                       selectedCategory: currentState.selectedCategory,
                                       recipes: currentRecipes,
                                       bottomRecipe: currentState.bottomRecipe,
                                       queue: currentState.queue)
        
        currentState = self.state.copy() as! RecipeListState
        self.onUpdateBottomRecipe(recipe: currentState.recipes[currentState.recipes.count-1])
    }
    
    private func resetSearchState() {
        let currentState = (self.state.copy() as! RecipeListState)
        var foodCategory = currentState.selectedCategory
        if(foodCategory?.value != currentState.query) {
            foodCategory = nil
        }
        self.state = self.state.doCopy(isLoading: currentState.isLoading, page: 1,
                                       query: currentState.query, selectedCategory: foodCategory,
                                       recipes: [], bottomRecipe: currentState.bottomRecipe,
                                       queue: currentState.queue)
    }
    
    private func onUpdateSelectedCategory(foodCategory: FoodCategory?) {
        let currentState = (self.state.copy() as! RecipeListState)
        self.state = self.state.doCopy(isLoading: currentState.isLoading, page: currentState.page,
                                       query: currentState.query, selectedCategory: foodCategory,
                                       recipes:currentState.recipes, bottomRecipe: currentState.bottomRecipe,
                                       queue: currentState.queue)
        
        onUpdateQuery(query: foodCategory?.value ?? "")
        onTriggerEvent(stateEvent: RecipeListEvents.NewSearch())
    }
    
    func shouldQueryNextPage(recipe: Recipe) -> Bool {
        // check if looking at the bottom recipe
          // if lookingAtBottom -> proceed
          // if PAGE_SIZE * page <= recipes.length
          // if !queryInProgress
          // else -> do nothing
        let currentState = self.state.copy() as! RecipeListState
        if(recipe.id == currentState.bottomRecipe?.id) {
            if(RecipeListState.Companion().RECIPE_PAGINATION_PAGE_SIZE * currentState.page <= currentState.recipes.count) {
                if(!currentState.isLoading) {
                    return true
                }
            }
        }
        return false
    }
    
    private func handleMessageByUIComponentType(_ message: GenericMessageInfo){
        switch message.uiComponentType {
        case UIComponentType.Dialog():
            appendToQueue(message: message)
        case UIComponentType.None():
            //send to analytics instead
            print("\(message.description)")
        default:
            doNothing()
        }
    }
    
    func doNothing() {
        //does nothing
    }
    
    /**
     *  Not everything can be conveniently updated with this function.
     *  Things like recipes and selectedCategory must have their own functions.
     *  Basically if more then one action must be taken then it cannot be updated with this function.
     *  ex: updating selected category requires us to 1) update category, 2) update the query, 3) trigger new search event
     */
    
    func updateState(
        isLoading: Bool? = nil,
        page: Int? = nil,
        query: String? = nil,
        bottomRecipe: Recipe? = nil,
        queue: Queue<GenericMessageInfo>? = nil
    ) {
        let currentState: RecipeListState = self.state.copy() as! RecipeListState
        
        self.state = self.state.doCopy(
            isLoading: isLoading ?? currentState.isLoading,
            page: Int32(page ?? Int(currentState.page)),
            query: query ?? currentState.query,
            selectedCategory: currentState.selectedCategory,
            recipes: currentState.recipes,
            bottomRecipe: bottomRecipe ?? currentState.bottomRecipe,
            queue: queue ?? currentState.queue
        )
        shouldShowDialog()
    }
    
    private func shouldShowDialog() {
        let currentState = self.state.copy() as! RecipeListState
        showDialog = currentState.queue.items.count > 0
    }
    
    private func appendToQueue(message: GenericMessageInfo) {
        let currentState = self.state.copy() as! RecipeListState
        let queue = currentState.queue
        let queueUtil = GenericMessageInfoQueueUtil()
        if !queueUtil.doesMessageAlreadyExistInQueue(queue: queue, messageInfo: message) {
            queue.add(element: message)
            updateState(queue: queue)
        }
    }
    
    func removeHeadFromQueue() {
        let currentState = self.state.copy() as! RecipeListState
        let queue = currentState.queue
        do {
            try queue.remove()
            updateState(queue: queue)
        }catch {
            print("\(error)")
        }
    }
}
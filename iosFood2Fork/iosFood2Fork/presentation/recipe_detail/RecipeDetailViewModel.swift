//
//  RecipeDetailViewModel.swift
//  iosFood2Fork
//
//  Created by Vusi Moyo on 2021/10/16.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

class RecipeDetailViewModel: ObservableObject {
    
    private let getRecipe: GetRecipe
    
    @Published var state: RecipeDetailState = RecipeDetailState()
    
    init(recipeId: Int, getRecipe: GetRecipe) {
        self.getRecipe = getRecipe
        onTriggeredEvent(stateEvent: RecipeDetailEvents.GetRecipe(recipeId: Int32(recipeId)))
    }
    
    func onTriggeredEvent(stateEvent: RecipeDetailEvents) {
        switch stateEvent {
        case is RecipeDetailEvents.GetRecipe:
            getRecipe(recipeId: Int((stateEvent as! RecipeDetailEvents.GetRecipe).recipeId))
        case is RecipeDetailEvents.OnRemoveHeadMessageFromQueue:
            doNothing()
        default: doNothing()
        }
    }
    
    private func getRecipe(recipeId: Int) {
        do{
            try self.getRecipe.execute(recipeId: Int32(recipeId))
                .collectCommon(coroutineScope: nil, callback: { dataState in
                    if(dataState != nil) {
                        let data = dataState?.data
                        let message = dataState?.message
                        let loading = dataState?.isLoading ?? false
                        self.updateState(isLoading: loading)
                        
                        if(data != nil) {
                            self.updateState(recipe: data! as Recipe)
                        }
                        if(message != nil) {
                            self.handleMessageByUIComponentType(message!)
                        }
                    } else {
                        print("GetRecipe: DataState is nil")
                    }
                })
        } catch {
            print("Get Recipe: ERROR: \(error)")
        }
    }
    
    private func doNothing() {
        //do nothing
    }
    
    private func updateState(isLoading: Bool? = nil, recipe: Recipe? = nil, queue: Queue<GenericMessageInfo>? = nil) {
        let currentState = (self.state.copy() as! RecipeDetailState)
        self.state = self.state.doCopy(isLoading: isLoading ?? currentState.isLoading, recipe: recipe ?? currentState.recipe, queue: queue ?? currentState.queue)
    }
    
    private func handleMessageByUIComponentType(_ message: GenericMessageInfo) {
        switch message.uiComponentType{
         case UIComponentType.Dialog():
             appendToQueue(message: message)
         case UIComponentType.None():
             print("\(message.description)")
         default:
             doNothing()
         }
    }
    
    private func appendToQueue(message: GenericMessageInfo) {
        let currentState = self.state.copy() as! RecipeDetailState
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

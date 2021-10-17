package za.co.kernelpanic.food2forkkmm.domain.model

sealed class UIComponentType {

    object Dialog : UIComponentType()

    object None : UIComponentType()
}
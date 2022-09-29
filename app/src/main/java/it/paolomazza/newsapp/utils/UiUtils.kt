package it.paolomazza.newsapp.utils

import android.view.View

object UiUtils {

    fun View.setVisibilityByCondition(condition:Boolean?){
        this.visibility = if(condition == true){
           View.VISIBLE
        }else{
            View.GONE
        }
    }

}
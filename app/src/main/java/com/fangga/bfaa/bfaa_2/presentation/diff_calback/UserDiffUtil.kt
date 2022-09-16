package com.fangga.bfaa.bfaa_2.presentation.diff_calback

import com.fangga.bfaa.bfaa_2.base.BaseDiffUtil
import com.fangga.bfaa.bfaa_2.data.model.User

class UserDiffUtil(
    oldList: List<User>,
    newList: List<User>
): BaseDiffUtil<User, String>(oldList,newList) {
    override fun User.getItemIdentifier(): String = this.username
}
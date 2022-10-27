package com.ys.jetsurvey.util

/**
 * 이벤트를 나타내는 LiveData를 통해 노출되는 데이터의 래퍼로 사용됩니다.
 */
data class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // 외부에서 읽는것은 허용하지만 쓰는것은 허용 불가

    /**
     * [content] 를 반환하고 재사용 방지
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * 처리 상태에 관계없이 [content]를 반환
     */
    fun peekContent(): T = content
}
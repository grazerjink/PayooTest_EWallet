package com.winsonmac.core.listener

import com.winsonmac.core.popup.MessagePopup

/**
 * Popup handler
 */
typealias PopupHandler = (MessagePopup) -> Unit


/**
 * Item handler in adapter
 */
typealias OnItemClickListener<T> = (T, Int) -> Unit
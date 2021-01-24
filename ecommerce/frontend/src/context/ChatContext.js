import { notification } from 'antd'
import constate from 'constate'
import { useState } from 'react'

import moment from 'moment'
import { api } from '../api'
import { formatConversation } from '../utils'

import useInterval from '@use-it/interval'

const getCounterpartAndConversation = (counterpart, conversations) => {
    let _counterpart = null
    let _conversation = null

    // this part is a bit complicated but if you follow it slowly you will understand if I store the
    // conversation object then on every poll a branch new object is created which makes the
    // conversation pane jump to the first conversation in the array even though it might be another
    // one holding a counterpart id instead doesn't change so the conversation is fetched based on
    // its counterpart id since a conversation is uniquely identified with its counterpart
    if (conversations === null || !conversations.length) {
        // both either null or empty array
        _counterpart = null
        _conversation = null
    } else {
        if (counterpart === null) {
            _conversation = conversations[0]
            _counterpart = _conversation.counterpart.id
        } else {
            _counterpart = counterpart
            _conversation = conversations.find(conversation => conversation.counterpart.id === _counterpart)

            if (_conversation === null) {
                _conversation = conversations[0]
                _counterpart = _conversation.counterpart.id
            } else {
                // do nothing
            }
        }
    }

    return { counterpart: _counterpart, conversation: _conversation }
}

function useChat() {
    const [conversations, setConversations] = useState(null)
    const [counterpart, setCounterpart] = useState(null)
    const [lastFetch, setLastFetch] = useState(moment())

    const setConversation = conversation => {
        setCounterpart(conversation.counterpart.id)
    }

    useInterval(async () => {
        await getConversations()
    }, 5000)

    const getConversations = async () => {
        // try {
        //     const { data } = await api.get(`/messages`)
        //     if (!data?.status?.successful) throw new Error(data)
        //     const conversations = data.conversations.map(formatConversation)
        //     setConversations(conversations)
        //     setLastFetch(moment())
        // } catch (error) {
        //     notification.warning({ message: 'There was an error while fetching messages' })
        //     console.error(error)
        // }
    }

    const sendMessage = async message => {
        const _msg = {
            receiver_id: message.receiver_id,
            text: message?.text ?? null,
            attachment: message?.attachment ?? null,
        }
        console.log('sendMessage message', _msg)
        try {
            const { data } = await api.post(`/messages`, _msg)
            if (!data?.status?.successful) throw new Error(data)
            await getConversations()
            return true
        } catch (error) {
            notification.error({ message: 'There was an error while sending message' })
            console.error(error)
            return false
        }
    }

    return {
        ...getCounterpartAndConversation(counterpart, conversations),
        setConversation,
        conversations,
        getConversations,
        sendMessage,
        lastFetch,
    }
}

export const [ChatContextProvider, useChatContext] = constate(useChat)

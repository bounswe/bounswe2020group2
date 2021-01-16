import './Conversation.less'
import 'react-chat-elements/dist/main.css'

import { UploadOutlined } from '@ant-design/icons'
import { Button, Input, Skeleton, Upload, Image, Empty } from 'antd'
import cls from 'classnames'
import { getBase64 } from 'image-blobber'
import { useEffect, useRef, useState, Fragment } from 'react'
import { MessageList } from 'react-chat-elements'

import { useChatContext } from '../../context/ChatContext'
import { useAppContext } from '../../context/AppContext'

const { TextArea } = Input

export const Conversation = ({ className, conversation }) => {
    const [fileList, setFileList] = useState([])
    const [value, setValue] = useState('')
    const [loading, setLoading] = useState(false)
    const { user } = useAppContext()
    const { lastFetch, sendMessage, conversations } = useChatContext()
    const [image, setImage] = useState(null)

    if (!conversations) {
        return (
            <div className={cls(className, 'conversation-skeleton')}>
                <Skeleton className={'conversation-skeleton_left'} active title={false} paragraph={{ rows: 2 }} />
                <Skeleton className={'conversation-skeleton_right'} active title={false} paragraph={{ rows: 1 }} />
                <Skeleton className={'conversation-skeleton_left'} active title={false} paragraph={{ rows: 1 }} />
                <Skeleton className={'conversation-skeleton_left'} active title={false} paragraph={{ rows: 2 }} />
                <Skeleton className={'conversation-skeleton_right'} active title={false} paragraph={{ rows: 1 }} />
                <Skeleton className={'conversation-skeleton_right'} active title={false} paragraph={{ rows: 2 }} />
                <Skeleton className={'conversation-skeleton_left'} active title={false} paragraph={{ rows: 1 }} />
                <Skeleton className={'conversation-skeleton_left'} active title={false} paragraph={{ rows: 1 }} />
                <Skeleton className={'conversation-skeleton_left'} active title={false} paragraph={{ rows: 1 }} />
                <Skeleton className={'conversation-skeleton_right'} active title={false} paragraph={{ rows: 2 }} />
            </div>
        )
    }

    if (!conversation) {
        return <Empty description={'Please select a conversation'} />
    }

    const formatText = text => {
        let arr = []
        for (const line of text.split('\n')) {
            arr.push(line)
            arr.push(<br />)
        }

        return (
            <>
                {arr.map((el, i) => {
                    return <Fragment key={i}>{el}</Fragment>
                })}
            </>
        )
    }

    const formatMessage = message => {
        const type = message.attachment_url !== null ? 'photo' : 'text'
        return {
            position: message.sent_by_me ? 'right' : 'left',
            type,
            titleColor: message.sent_by_me ? '#472836' : '#e2be5a',
            title: message.sent_by_me
                ? [user.name, user.lastname].filter(Boolean).join(' ')
                : [conversation.counterpart.name, conversation.counterpart.lastname].filter(Boolean).join(' '),
            text: formatText(message.text),
            date: message.date.toDate(),
            notch: true,
            ...(type === 'photo' ? { data: { uri: message.attachment_url } } : {}),
        }
    }

    const onInputChange = event => {
        setValue(event.target.value)
    }

    const onSendMessage = async event => {
        if (event.shiftKey) return

        let message = {
            receiver_id: conversation.counterpart.id,
            text: value.trim(),
        }

        if (fileList.length) {
            const image = await getBase64(fileList[0].originFileObj)
            message.attachment = image.base64.split(',')[1]
        }

        try {
            setLoading(true)
            await sendMessage(message)
            setValue('')
            setFileList([])
        } finally {
            setLoading(false)
        }
    }

    const onImageOpen = (data, event) => {
        setImage(data.data.uri)
    }

    const handlePreview = async file => {
        if (!file.preview) {
            const image = await getBase64(file.originFileObj)
            file.preview = image.base64
        }

        setImage(file.preview)
    }

    return (
        <div className={className}>
            <div className={'conversation-header'}>Last updated at: {lastFetch.format('hh:mm:ss')}</div>
            <MessageList
                className="conversation-message-list"
                lockable={true}
                toBottomHeight={'100%'}
                dataSource={conversation.messages.map(formatMessage)}
                onOpen={onImageOpen}
            />

            <Image
                height={0}
                width={0}
                src={image}
                preview={{
                    visible: !!image,
                    onVisibleChange: (o, n) => setImage(null),
                    src: image,
                }}
            />
            <div className="conversation-input-container">
                <Upload
                    accept=".jpg"
                    listType={fileList.length ? 'picture-card' : 'picture'}
                    fileList={fileList}
                    onPreview={handlePreview}
                    onChange={async ({ fileList }) => {
                        if (!fileList.length) return setFileList([])

                        const file = fileList[fileList.length - 1]

                        setFileList([file])
                    }}
                    beforeUpload={file => {
                        setFileList([file])
                        return false
                    }}>
                    {!fileList.length && <Button icon={<UploadOutlined />}>Upload</Button>}
                </Upload>
                <TextArea
                    value={value}
                    onChange={onInputChange}
                    onPressEnter={onSendMessage}
                    placeholder="Enter your message here..."
                    autoSize
                />
                <Button type="primary" onClick={onSendMessage} loading={loading}>
                    Send
                </Button>
            </div>
        </div>
    )
}

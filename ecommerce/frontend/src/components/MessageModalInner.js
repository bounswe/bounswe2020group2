import { useState } from 'react'
import { notification, Button, Upload, Input } from 'antd'
import { useChatContext } from '../context/ChatContext'
import { UploadOutlined } from '@ant-design/icons'
import { getBase64, getImageData } from 'image-blobber'
import './MessageModalInner.less'

const { TextArea } = Input

export const MessageModalInner = ({ receiverId, onFinish }) => {
    const { sendMessage } = useChatContext()
    const [isLoading, setIsLoading] = useState(false)

    const [fileList, setFileList] = useState([])
    const [text, setText] = useState('')

    const onSend = async () => {
        try {
            setIsLoading(true)

            let message = {
                receiver_id: receiverId,
                text: text.trim(),
            }

            if (fileList.length) {
                const image = await getBase64(fileList[0].originFileObj)
                message.attachment = image.base64.split(',')[1]
            }

            if (await sendMessage(message)) onFinish()
        } catch (error) {
            notification.error({ description: 'There was an error with your request.' })
        } finally {
            setIsLoading(false)
        }
    }

    const onTextChange = event => setText(event.target.value)

    return (
        <div className="message-modal">
            <div className="message-modal-text">
                <TextArea
                    value={text}
                    onChange={onTextChange}
                    rows={4}
                    placeholder="Write your message here"
                    onChange={onTextChange}
                    autoSize
                />
            </div>

            <div className="message-modal-upload">
                <Upload
                    accept=".jpg"
                    listType="picture"
                    fileList={fileList}
                    onChange={({ fileList }) => {
                        if (fileList.length === 0) {
                            setFileList(fileList)
                        } else {
                            setFileList([fileList[fileList.length - 1]])
                        }
                    }}
                    beforeUpload={file => {
                        setFileList([file])
                        return false
                    }}>
                    <Button icon={<UploadOutlined />}>Attach an image</Button>
                </Upload>
            </div>

            <div className="message-modal-button">
                <Button loading={isLoading} type="primary" onClick={onSend}>
                    Send Message
                </Button>
            </div>
        </div>
    )
}

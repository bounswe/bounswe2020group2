import { useAppContext } from '../../context/AppContext'
import React, { useEffect, useState } from 'react'
import { Spin, Button, Rate, Switch, Modal } from 'antd'
import { api } from '../../api'
import { useHistory } from 'react-router-dom'
import { EditOutlined } from '@ant-design/icons'
import { round, getVendorRatingLevel } from '../../utils'
import { MessageModalInner } from '../MessageModalInner'
import './VendorSplash.less'

export const VendorSplash = ({ vendorId, onEditModeChange, editable }) => {
    const [vendorHeaderDetails, setVendorHeaderDetails] = useState({})
    const [isLoading, setIsLoading] = useState(true)

    const { user } = useAppContext()
    const isVendorAndOwner = user.type === 'vendor' && vendorId === user.id
    const history = useHistory()
    const [messageModalVisible, setMessageModalVisible] = useState(false)

    useEffect(() => {
        async function fetch() {
            try {
                setIsLoading(true)
                const { data } = await api.get(`/vendor/${vendorId}/details`)
                setVendorHeaderDetails(data)
            } catch (error) {
                console.error(error)
            } finally {
                setIsLoading(false)
            }
        }
        fetch()
    }, [])

    const onMessageEnd = () => {
        setMessageModalVisible(false)
        // history.push('/profile/messages/')
    }

    const onMessageClick = event => {
        event.stopPropagation()
        setMessageModalVisible(true)
    }
    const { title, image_url, description, rating } = vendorHeaderDetails
    return (
        <Spin spinning={isLoading}>
            <Modal
                destroyOnClose
                title={`Send a message to ${title}`}
                visible={messageModalVisible}
                onOk={onMessageClick}
                onCancel={onMessageEnd}
                footer={null}>
                <MessageModalInner receiverId={vendorId} onFinish={onMessageEnd} />
            </Modal>
            <div className="vendor-splash">
                <div className="vendor-image">
                    <img src={image_url} className="splash-logo"></img>
                </div>
                <div className="vendor-header">
                    <div className="vendor-header-content">
                        <h1 className="vendor-name">{title}</h1>
                        <h3 className="vendor-slogan">{description}</h3>
                        <div
                            className={`product-header-vendor-rating product-header-vendor-rating__${getVendorRatingLevel(
                                rating,
                            )}`}>
                            {round(rating)}
                        </div>
                    </div>
                    <div className="vendor-edit-button">
                        {isVendorAndOwner ? (
                            <div className="vendor-edit-mode-open">
                                <p>{editable ? 'Edit Mode' : 'Seeing as Customer'}</p>
                                <Switch defaultChecked={true} onChange={onEditModeChange} />
                            </div>
                        ) : (
                            <Button type="primary" icon={<EditOutlined />} onClick={onMessageClick}>
                                Send a message
                            </Button>
                        )}
                    </div>
                </div>
            </div>
        </Spin>
    )
}

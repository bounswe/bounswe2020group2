import './VendorSplash.less'

import { EditOutlined } from '@ant-design/icons'
import { Button, Modal, Spin, Switch, Typography } from 'antd'
import React, { useEffect, useState } from 'react'
import { useHistory } from 'react-router-dom'

import { api } from '../../api'
import { useAppContext } from '../../context/AppContext'
import { formatImageUrl, formatVendorDetails, getVendorRatingLevel, round } from '../../utils'
import { MessageModalInner } from '../MessageModalInner'

export const VendorSplash = ({ vendorId, onEditModeChange, editable }) => {
    const [vendorHeaderDetails, setVendorHeaderDetails] = useState({})
    const [isLoading, setIsLoading] = useState(true)

    const { user } = useAppContext()
    const isVendorAndOwner = user.type === 'vendor' && vendorId === user.id
    const [messageModalVisible, setMessageModalVisible] = useState(false)

    const { Paragraph } = Typography

    const fetch = async () => {
        try {
            setIsLoading(true)
            const { data } = await api.get(`/vendor/${vendorId}/details`)
            setVendorHeaderDetails(formatVendorDetails(data))
        } catch (error) {
            console.error(error)
        } finally {
            setIsLoading(false)
        }
    }

    useEffect(() => {
        fetch()
    }, [])

    const onMessageEnd = () => {
        setMessageModalVisible(false)
    }

    const onMessageClick = event => {
        event.stopPropagation()
        setMessageModalVisible(true)
    }
    const { title, image_url, description, rating } = vendorHeaderDetails

    const onEdit = field => async value => {
        const newDetails = {
            ...vendorHeaderDetails,
            [field]: value,
        }

        console.log(newDetails)

        try {
            setIsLoading(true)
            await api.put(`/vendor/${vendorId}/details`, newDetails)
            await fetch()
        } catch (error) {
            console.error(error)
        } finally {
            setIsLoading(false)
        }
    }

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
                    <img
                        src={formatImageUrl(
                            image_url ??
                                'https://github.com/bounswe/bounswe2020group2/raw/master/milestone1/logo_circle.png',
                        )}
                        className="splash-logo"></img>
                </div>
                <div className="vendor-header">
                    <div className="vendor-header-content">
                        <h1 className="vendor-name">
                            <Paragraph editable={editable ? { onChange: onEdit('title'), maxLength: 100 } : undefined}>
                                {title}
                            </Paragraph>
                            <div
                                className={`product-header-vendor-rating product-header-vendor-rating__${getVendorRatingLevel(
                                    rating,
                                )}`}>
                                {round(rating)}
                            </div>
                        </h1>

                        <h3 className="vendor-slogan">
                            <Paragraph
                                editable={editable ? { onChange: onEdit('description'), maxLength: 100 } : undefined}>
                                {description}
                            </Paragraph>
                        </h3>
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

import { useAppContext } from '../../context/AppContext'
import React, { useEffect, useState } from 'react'
import { Spin, Button, Rate, Switch, Modal } from 'antd'
import { api } from '../../api'
import { useHistory } from 'react-router-dom'
import { EditOutlined } from '@ant-design/icons'
import { round, getVendorRatingLevel, formatImageUrl } from '../../utils'
import { MessageModalInner } from '../MessageModalInner'
import './VendorSplash.less'

import { Typography } from 'antd'

export const VendorSplash = ({ vendorId, onEditModeChange, editable }) => {
    const [vendorHeaderDetails, setVendorHeaderDetails] = useState({})
    const [isLoading, setIsLoading] = useState(true)

    const { user } = useAppContext()
    const isVendorAndOwner = user.type === 'vendor' && vendorId === user.id
    const history = useHistory()
    const [messageModalVisible, setMessageModalVisible] = useState(false)

    const { Paragraph } = Typography

    useEffect(() => {
        async function fetch() {
            try {
                setIsLoading(true)
                console.log('asdasd')
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
    const { title, image_url, description, rating_count } = vendorHeaderDetails
    const rating = rating_count
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
                            <Paragraph editable={!editable}>{title}</Paragraph>
                            <div
                                className={`product-header-vendor-rating product-header-vendor-rating__${getVendorRatingLevel(
                                    rating,
                                )}`}>
                                {round(rating)}
                            </div>
                        </h1>

                        <h3 className="vendor-slogan">
                            <Paragraph editable={!editable}>{description}</Paragraph>
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
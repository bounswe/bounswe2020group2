import { useAppContext } from '../../context/AppContext'
import React, { useEffect, useState } from 'react'
import { Spin, Button, Rate, Switch } from 'antd'
import { api } from '../../api'
import { useHistory } from 'react-router-dom'
import { EditOutlined } from '@ant-design/icons'
import { round } from '../../utils'
import './VendorSplash.less'

const getVendorRatingLevel = ({ rating }) => {
    if (rating <= 5.0) {
        return 'low'
    }
    if (rating <= 8.0) {
        return 'medium'
    }
    return 'high'
}

export const VendorSplash = ({ vendorId, onEditModeChange, editable }) => {
    const [vendorHeaderDetails, setVendorHeaderDetails] = useState({})
    const [isLoading, setIsLoading] = useState(true)

    const { user } = useAppContext()
    const isVendorAndOwner = user.type === 'vendor' && vendorId === user.id.toString()
    const history = useHistory()

    useEffect(() => {
        async function fetch() {
            try {
                setIsLoading(true)
                const {
                    data: { data },
                } = await api.get(`/vendor/${vendorId}/details`)
                setVendorHeaderDetails(data)
            } catch (error) {
                console.error(error)
            } finally {
                console.log('hi')
                setIsLoading(false)
            }
        }
        fetch()
    }, [])

    const onSendMessage = () => {
        // Send message modal popup
        // Sends the message from modal
        // Go to profile/messages
        history.push('/profile/messages/')
    }
    const { title, image_url, description, rating } = vendorHeaderDetails
    return (
        <Spin spinning={isLoading}>
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
                                {
                                    rating,
                                },
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
                            <Button
                                type="primary"
                                icon={<EditOutlined />}
                                onClick={() => {
                                    onSendMessage()
                                }}>
                                Send a message
                            </Button>
                        )}
                    </div>
                </div>
            </div>
        </Spin>
    )
}

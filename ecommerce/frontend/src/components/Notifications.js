import './Notifications.less'
import { useAppContext } from '../context/AppContext'
import { useEffect, useState } from 'react'
import { Button, Spin } from 'antd'
import { formatProduct, productSortBy, sleep } from '../utils'
import { api } from '../api'
import { format } from 'prettier'
import moment from 'moment'
import { Link } from 'react-router-dom'
import { EditOutlined, BellOutlined, BellTwoTone, DeleteOutlined, DeleteTwoTone, EditTwoTone } from '@ant-design/icons'

import { notifications } from '../mocks/mocks'
import './Notifications.less'

const onSnoozeAllNotifications = () => {}

const onSeenNotification = () => {}

const onDeleteAllNotifications = () => {}

export const Notifications = () => {
    const [notifications, setNotifications] = useState([])
    useEffect(() => {
        const fetch = async () => {
            const { data } = await api.get('/notifications')
            setNotifications(data)
            console.log(data)
        }
        try {
            fetch()
        } catch (error) {
            console.error(error)
        }
    }, [])
    const unseenNotifications = notifications.filter(item => !item.is_seen)
    return (
        <div>
            <div className="notifications-header">
                <h3>{'Your notifications (' + unseenNotifications.length + ')'}</h3>
                <div>
                    {unseenNotifications.length != 0 && (
                        <Button
                            type="text"
                            icon={<BellTwoTone twoToneColor="#52c41a" />}
                            onClick={onSnoozeAllNotifications}>
                            Snooze All
                        </Button>
                    )}
                    {notifications.length != 0 && (
                        <Button
                            type="text"
                            icon={<DeleteTwoTone twoToneColor="#F35A22" />}
                            onClick={onDeleteAllNotifications}>
                            Delete All
                        </Button>
                    )}
                </div>
            </div>
            <div className="notifications-container">
                {notifications.map(notification => {
                    console.log(notification)
                    const product =
                        notification.type === 'price_change' ? notification.argument : notification.argument.product
                    console.log('product: ', product)
                    return (
                        <div key={notification.id} className="notification-item">
                            <div
                                className={`notification-status notification-status__${
                                    notification.is_seen ? 'seen' : 'unseen'
                                }`}></div>
                            <div className="notification-image">
                                <img
                                    src={
                                        notification.type === 'price_change'
                                            ? product.image_url
                                            : 'data:image/jpeg;base64,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'
                                    }
                                />
                            </div>
                            <div className="notification-message">
                                <div>
                                    <p>{notification.text}</p>
                                    <Link to={`/product/${product.id}`}>See new deal now. </Link>{' '}
                                </div>
                                <div className="notification-date">{moment(notification.date).fromNow()}</div>
                            </div>
                            {!notification.is_seen && (
                                <div className="notification-option-icons">
                                    <Button
                                        type="default"
                                        icon={<BellTwoTone twoToneColor="#52c41a" />}
                                        onClick={onSeenNotification}>
                                        Mark as Seen
                                    </Button>
                                </div>
                            )}
                        </div>
                    )
                })}
            </div>
        </div>
    )
}

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
            console.log(data)
            setNotifications(data)
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
                    <Button
                        type="text"
                        icon={<BellTwoTone twoToneColor="#52c41a" />}
                        onClick={onSnoozeAllNotifications}>
                        Snooze All
                    </Button>
                    <Button
                        type="text"
                        icon={<DeleteTwoTone twoToneColor="#F35A22" />}
                        onClick={onDeleteAllNotifications}>
                        Delete All
                    </Button>
                </div>
            </div>
            <div className="notifications-container">
                {notifications.map(notification => {
                    return (
                        <div key={notification.id} className="notification-item">
                            <div
                                className={`notification-status notification-status__${
                                    notification.is_seen ? 'seen' : 'unseen'
                                }`}></div>
                            <div className="notification-image">
                                <img src={notification.product.images[0]} />
                            </div>
                            <div className="notification-message">
                                <div>
                                    <p>{notification.message}</p>
                                    <Link to={`/product/${notification.product.id}`}>See new deal now. </Link>{' '}
                                </div>
                                <div className="notification-date">{moment(notification.date).fromNow()}</div>
                            </div>
                            <div className="notification-option-icons">
                                <Button
                                    type="default"
                                    icon={<BellTwoTone twoToneColor="#52c41a" />}
                                    onClick={onSeenNotification}>
                                    Mark as Seen
                                </Button>
                            </div>
                        </div>
                    )
                })}
            </div>
        </div>
    )
}

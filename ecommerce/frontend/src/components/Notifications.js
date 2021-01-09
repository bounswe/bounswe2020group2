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

const onSeenAllNotifications = () => {}

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
                            onClick={onSeenAllNotifications}>
                            Mark All as Seen
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
                                            : 'https://github.com/bounswe/bounswe2020group2/blob/master/images/order_update.jpg?raw=true'
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

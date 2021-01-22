import './Notifications.less'
import { useEffect, useState } from 'react'
import { Button, Spin } from 'antd'
import { api } from '../api'
import moment from 'moment'
import { Link } from 'react-router-dom'
import { BellTwoTone, DeleteTwoTone } from '@ant-design/icons'
import './Notifications.less'

const onSeenAllNotifications = () => {}

const onSeenNotification = () => {}

const onDeleteAllNotifications = () => {}

const priceChangeTextTemplate = (title, old_price, new_price) => {
    return <p>{`Price of a product in your wishlist ( ${title} ) has changed from ${old_price}TL to ${new_price}TL`}</p>
}

export const Notifications = () => {
    const [notifications, setNotifications] = useState([])

    useEffect(() => {
        const fetch = async () => {
            const { data } = await api.get('/notifications')
            console.log('notification', data)
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
                    const product =
                        notification.type === 'price_change' ? notification.argument : notification.argument.product
                    return (
                        <div key={notification.id} className="notification-item">
                            <div
                                className={`notification-status notification-status__${
                                    notification.is_seen ? 'seen' : 'unseen'
                                }`}></div>
                            <div className="notification-image">
                                <Link to={`/product/${product.id}`}>
                                    <img
                                        src={
                                            notification.type === 'price_change'
                                                ? product.image_url
                                                : 'https://github.com/bounswe/bounswe2020group2/blob/master/images/order_update.jpg?raw=true'
                                        }
                                    />
                                </Link>{' '}
                            </div>
                            <div className="notification-message">
                                <div>
                                    <p>
                                        {priceChangeTextTemplate(product.title, product.old_price, product.new_price)}
                                    </p>
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

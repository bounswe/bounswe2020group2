import './Notifications.less'
import { useEffect, useState } from 'react'
import { Button, Spin } from 'antd'
import { api } from '../api'
import moment from 'moment'
import { Link } from 'react-router-dom'
import { BellTwoTone, DeleteTwoTone } from '@ant-design/icons'
import './Notifications.less'

const onDeleteAllNotifications = () => {
    console.log('delete all notifications')
    // const fetch = async () => {
    //     const { data } = await api.post('/notifications')
    // }
    // try {
    //     fetch()
    // } catch (error) {
    //     console.error(error)
    // } finally {
    //     // TODO add success message
    // }
}

const priceChangeTextTemplate = (title, old_price, new_price) => {
    if (old_price < new_price) {
        return 'Price of a product in your wishlist has changed.'
    } else {
        return 'A product in your wishlist is now cheaper. Don’t miss out!'
    }
}
const orderStatusChangeTextTemplate = (title, old_status, new_status) => {
    if (new_status == 'at_cargo') {
        return 'Your order is now on the way.'
    } else if (new_status == 'delivered') {
        return 'Your order is delivered. Enjoy!'
    } else if (new_status == 'accepted') {
        return 'Your order is accepted.'
    }
    return `Your order status has changed.`
}

const priceChangeDetail = product => {
    return {
        title: priceChangeTextTemplate(product.title, product.old_price, product.new_price),
        link: `/product/${product.id}`,
        image_url: product.image_url,
        link_text: 'See the product',
    }
}

const orderStatusChangeDetail = order => {
    return {
        title: orderStatusChangeTextTemplate(order.title, order.old_status, order.new_status),
        link: '/profile/orders',
        image_url: 'https://github.com/bounswe/bounswe2020group2/blob/master/images/order_update.jpg?raw=true',
        link_text: 'Go to Orders page',
    }
}
export const Notifications = () => {
    const [isLoading, setIsLoading] = useState(true)
    const [notifications, setNotifications] = useState([])
    const [notificationsRefreshId, setNotificationsRefreshId] = useState(0)

    useEffect(() => {
        const fetch = async () => {
            setIsLoading(true)
            const { data } = await api.get('/notifications')
            setNotifications(data)
        }
        try {
            fetch()
        } catch (error) {
            console.error(error)
        } finally {
            setIsLoading(false)
        }
    }, [notificationsRefreshId])
    const unseenNotifications = notifications.filter(item => !item.is_seen)

    const onSeenAllNotifications = () => {
        console.log('mark all as seen: ')
        const fetch = async () => {
            const { data } = await api.post('/notifications/seen/')
        }
        try {
            fetch()
        } catch (error) {
            console.error(error)
        } finally {
            // TODO add success message
        }
        setNotificationsRefreshId(notificationsRefreshId + 1)
    }

    const onSeenNotification = notificationId => {
        console.log('mark as seen: ', notificationId)
        const fetch = async () => {
            const { data } = await api.post(`/notifications/seen/${notificationId}`)
        }
        try {
            fetch()
        } catch (error) {
            console.error(error)
        } finally {
            // TODO add success message
        }
        setNotificationsRefreshId(notificationsRefreshId + 1)
    }

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
                <Spin spinning={isLoading}>
                    {notifications.map(notification => {
                        const notificationDetail =
                            notification.type === 'price_change'
                                ? priceChangeDetail(notification.argument)
                                : orderStatusChangeDetail(notification.argument)
                        return (
                            <div key={notification.id} className="notification-item">
                                <div
                                    className={`notification-status notification-status__${
                                        notification.is_seen ? 'seen' : 'unseen'
                                    }`}></div>
                                <div className="notification-image">
                                    <Link to={`${notificationDetail.link}`}>
                                        <img src={notificationDetail.image_url} />
                                    </Link>{' '}
                                </div>
                                <div className="notification-message">
                                    <div>
                                        <p>{notificationDetail.title}</p>
                                        <Link to={notificationDetail.link}>{notificationDetail.link_text}</Link>{' '}
                                    </div>
                                    <div className="notification-date">{moment(notification.date).fromNow()}</div>
                                </div>
                                {!notification.is_seen && (
                                    <div className="notification-option-icons">
                                        <Button
                                            type="default"
                                            icon={<BellTwoTone twoToneColor="#52c41a" />}
                                            onClick={() => {
                                                onSeenNotification(notification.id)
                                            }}>
                                            Mark as Seen
                                        </Button>
                                    </div>
                                )}
                            </div>
                        )
                    })}
                </Spin>
            </div>
        </div>
    )
}

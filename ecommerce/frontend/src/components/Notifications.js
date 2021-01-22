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
    return <p>{`Price of ${title} has changed from ${old_price}TL to ${new_price}TL`}</p>
}
const orderStatusChangeTextTemplate = (title, old_status, new_status) => {
    return <p>{`A status of your order has changed from ${old_status} to ${new_status}`}</p>
}

const priceChangeDetail = product => {
    return {
        title: priceChangeTextTemplate(product.title, product.old_price, product.new_price),
        link: product.id,
        image_url: product.image_url,
    }
}

const orderStatusChangeDetail = order => {
    return {
        title: orderStatusChangeTextTemplate(order.title, order.old_status, order.new_status),
        link: order.id,
        image_url: order.product.image_url,
    }
}
export const Notifications = () => {
    const [isLoading, setIsLoading] = useState(true)
    const [notifications, setNotifications] = useState([])

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
                <Spin spinning={isLoading}>
                    {notifications.map(notification => {
                        const product =
                            notification.type === 'price_change' ? notification.argument : notification.argument.product
                        const notificationDetail =
                            notification.type === 'price_change'
                                ? priceChangeDetail(notification.argument)
                                : orderStatusChangeDetail(notification.argument)
                        console.log(notificationDetail)
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
                                            {priceChangeTextTemplate(
                                                product.title,
                                                product.old_price,
                                                product.new_price,
                                            )}
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
                </Spin>
            </div>
        </div>
    )
}

import './Purchase.less'

import { Button, Collapse, Modal, notification, Select } from 'antd'
import moment from 'moment'
import { useState } from 'react'
import { Link } from 'react-router-dom'

import { api } from '../../api'
import { useAppContext } from '../../context/AppContext'
import { formatOrderStatus, orderStatusInvMap, orderStatusDisplayMapping, formatPrice } from '../../utils'
import { HeaderLabel } from '../HeaderLabel'
import { MessageModalInner } from '../MessageModalInner'
import { UserReviewPost } from '../UserReview/UserReviewPost'

export const PurchaseVendor = ({ purchase, onPurchaseUpdated = () => {} }) => {
    const { product, status } = purchase
    const [messageModalVisible, setMessageModalVisible] = useState(false)
    const [isLoading, setIsLoading] = useState(false)

    const receiver = [purchase.address.name, purchase.address.surname].filter(Boolean).join(' ')

    const statusName = orderStatusInvMap[status]

    const onMessageClick = event => {
        event.stopPropagation()
        setMessageModalVisible(true)
    }

    const onMessageEnd = () => {
        setMessageModalVisible(false)
    }

    const user = {
        receiverId: 9,
        name: receiver,
    }

    const onChangeOrderStatus = async newStatus => {
        try {
            setIsLoading(true)
            const {
                data: { status },
            } = await api.put(`/vendor/order`, {
                orderId: purchase.id,
                orderStatus: newStatus,
            })
            if (status.successful) {
                notification.success({ message: status.message })
                onPurchaseUpdated()
            } else {
                notification.warning({ message: status.message })
            }
        } catch (error) {
            notification.error({ description: 'There was an error with your request.' })
        } finally {
            setIsLoading(false)
        }
    }

    return (
        <>
            <Collapse defaultActiveKey="purchase">
                <Collapse.Panel
                    className={`purchase-panel-header--${statusName}`}
                    key="purchase"
                    showArrow={false}
                    header={
                        <div className="purchase-header">
                            <HeaderLabel label="Date">
                                {moment.utc(purchase.purchase_date).format('DD/MM/YYYY HH:mm ')}
                            </HeaderLabel>
                            <HeaderLabel label="Status">
                                <Select
                                    loading={isLoading}
                                    onClick={e => e.stopPropagation()}
                                    value={statusName}
                                    onChange={onChangeOrderStatus}>
                                    {Object.entries(orderStatusDisplayMapping).map(([k, v]) => {
                                        return (
                                            <Select.Option key={k} value={k}>
                                                {v}
                                            </Select.Option>
                                        )
                                    })}
                                </Select>
                            </HeaderLabel>
                            <HeaderLabel label="Address">{purchase.address.title}</HeaderLabel>
                            <HeaderLabel label="Price">
                                {formatPrice(purchase.unit_price)} {purchase.currency ?? 'TL'}
                            </HeaderLabel>
                            <HeaderLabel label="Amount">{purchase.amount}</HeaderLabel>
                            <HeaderLabel label="Receiver">{receiver}</HeaderLabel>
                            <Button className="purchase-header-extra" onClick={onMessageClick} type="primary">
                                Message
                            </Button>
                        </div>
                    }>
                    <div className="purchase-product">
                        <div className="purchase-product-picture">
                            <Link to={`/product/${product.id}`}>
                                <img alt={product.name} width={'100%'} src={product.images[0]}></img>
                            </Link>
                        </div>
                        <div className="purchase-product-details">
                            <div className="purchase-product-details-title">
                                <Link to={`/product/${product.id}`}>{product.title}</Link>
                            </div>
                            <div className="purchase-product-details-description">{product.short_description}</div>
                        </div>
                    </div>
                </Collapse.Panel>
            </Collapse>
            <Modal
                destroyOnClose
                title={`Send a message to ${user.name}`}
                visible={messageModalVisible}
                onOk={onMessageClick}
                onCancel={onMessageEnd}
                footer={null}>
                <MessageModalInner receiverId={user.receiverId} onFinish={onMessageEnd} />
            </Modal>
        </>
    )
}

export const PurchaseCustomer = ({ purchase }) => {
    const { product, status } = purchase

    const [messageModalVisible, setMessageModalVisible] = useState(false)
    const [reviewModalVisible, setReviewModalVisible] = useState()

    const statusName = orderStatusInvMap[status]

    const onMessageClick = event => {
        event.stopPropagation()
        setMessageModalVisible(true)
    }

    const onMessageEnd = () => {
        setMessageModalVisible(false)
    }

    const user = {
        receiverId: 55,
        name: purchase.vendor.name,
    }

    const receiver = [purchase.address.name, purchase.address.surname].filter(Boolean).join(' ')

    const canReview = orderStatusInvMap[status] === 'delivered' // needs to check if already reviewd

    const onReviewClick = event => {
        event.stopPropagation()
        setReviewModalVisible(true)
    }

    const onReviewEnd = () => {
        setReviewModalVisible(false)
    }

    return (
        <>
            <Collapse defaultActiveKey="purchase">
                <Collapse.Panel
                    className={`purchase-panel-header--${statusName}`}
                    key="purchase"
                    showArrow={false}
                    header={
                        <div className="purchase-header">
                            <HeaderLabel label="Status">{formatOrderStatus(status)}</HeaderLabel>
                            <HeaderLabel label="Address">{purchase.address.title}</HeaderLabel>
                            <HeaderLabel label="Price">
                                {formatPrice(purchase.unit_price)} {purchase.currency ?? 'TL'}
                            </HeaderLabel>
                            <HeaderLabel label="Amount">{purchase.amount}</HeaderLabel>
                            <HeaderLabel label="Receiver">{receiver}</HeaderLabel>
                            {canReview && (
                                <Button className="purchase-header-extra" onClick={onReviewClick} type="primary">
                                    Review
                                </Button>
                            )}
                            &nbsp;
                            <Button className="purchase-header-extra" onClick={onMessageClick}>
                                Message
                            </Button>
                        </div>
                    }>
                    <div className="purchase-product">
                        <div className="purchase-product-picture">
                            <Link to={`/product/${product.id}`}>
                                <img alt={product.name} width={'100%'} src={product.images[0]}></img>
                            </Link>
                        </div>
                        <div className="purchase-product-details">
                            <div className="purchase-product-details-title">
                                <Link to={`/product/${product.id}`}>{product.title}</Link>
                            </div>
                            <div className="purchase-product-details-description">
                                {product.short_description}
                                <p>
                                    by{' '}
                                    <Link to={`/vendors/${product.vendor?.id ?? 'some_vendor_id'}`}>
                                        {product.vendor?.name ?? 'getflix'}
                                    </Link>
                                </p>
                            </div>
                        </div>
                    </div>
                </Collapse.Panel>
            </Collapse>
            <Modal destroyOnClose visible={reviewModalVisible} onOk={onReviewEnd} onCancel={onReviewEnd} footer={null}>
                <UserReviewPost product={purchase.product} onFinish={onReviewEnd} />
            </Modal>

            <Modal
                destroyOnClose
                title={`Send a message to ${user.name}`}
                visible={messageModalVisible}
                onOk={onMessageClick}
                onCancel={onMessageEnd}
                footer={null}>
                <MessageModalInner receiverId={user.receiverId} onFinish={onMessageEnd} />
            </Modal>
        </>
    )
}

export const Purchase = ({ purchase, onPurchaseUpdated }) => {
    const { user } = useAppContext()

    if (user.type === 'customer') {
        return <PurchaseCustomer purchase={purchase} />
    }

    if (user.type === 'vendor') {
        return <PurchaseVendor purchase={purchase} onPurchaseUpdated={onPurchaseUpdated} />
    }

    return null
}

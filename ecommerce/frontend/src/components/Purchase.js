import './Purchase.less'

import { Button, Collapse, Modal } from 'antd'
import { useState } from 'react'
import { Link } from 'react-router-dom'

import { formatOrderStatus, orderStatusInvMap } from '../utils'
import { HeaderLabel } from './HeaderLabel'
import { UserReviewPost } from './UserReview/UserReviewPost'

export const Purchase = ({ purchase }) => {
    const { product, status } = purchase

    const [reviewModalVisible, setReviewModalVisible] = useState()

    const receiver = [purchase.address.name, purchase.address.surname].filter(Boolean).join(' ')

    const canReview = orderStatusInvMap[status] === 'delivered' // needs to check if already reviewd

    const onReviewClick = event => {
        event.stopPropagation()
        setReviewModalVisible(true)
    }

    const onReviewEnd = () => {
        setReviewModalVisible(false)
    }

    const statusName = orderStatusInvMap[status]

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
                                {purchase.unit_price} {purchase.currency ?? 'TL'}
                            </HeaderLabel>
                            <HeaderLabel label="Amount">{purchase.amount}</HeaderLabel>
                            <HeaderLabel label="Receiver">{receiver}</HeaderLabel>
                            {canReview && (
                                <Button className="purchase-header-extra" onClick={onReviewClick} type="primary">
                                    Review
                                </Button>
                            )}
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
        </>
    )
}

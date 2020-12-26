import { Button, Collapse, Modal } from 'antd'
import useModal from 'antd/lib/modal/useModal'
import { useState } from 'react'
import { Link } from 'react-router-dom'
import { orderStatusInvMap, formatOrderStatus } from '../utils'
import './Purchase.less'
import { UserReviewPost } from './UserReview/UserReviewPost'

const HorizontalLabel = ({ label, children }) => {
    return (
        <div>
            <span className="horizontal-label">{label}:&nbsp;</span>
            <br />
            <span className="horizontal-text">{children}</span>
        </div>
    )
}

export const Purchase = ({ purchase }) => {
    const { product, status } = purchase

    // useModal()
    const [reviewModalVisible, setReviewModalVisible] = useState()

    const receiver = [purchase.address.name, purchase.address.surname].filter(Boolean).join(' ')

    const canReview = true || orderStatusInvMap[status] === 'delivered' // needs to check if already reviewd

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
                    key="purchase"
                    showArrow={false}
                    header={
                        <div className="purchase-header">
                            <HorizontalLabel label="Status">{formatOrderStatus(status)}</HorizontalLabel>
                            <HorizontalLabel label="Address">{purchase.address.title}</HorizontalLabel>
                            <HorizontalLabel label="Price">
                                {purchase.unit_price} {purchase.currency ?? 'TL'}
                            </HorizontalLabel>
                            <HorizontalLabel label="Amount">{purchase.amount}</HorizontalLabel>
                            <HorizontalLabel label="Receiver">{receiver}</HorizontalLabel>
                            {canReview && (
                                <Button className="purchase-header-extra" onClick={onReviewClick}>
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

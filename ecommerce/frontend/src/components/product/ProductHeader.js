import './ProductHeader.less'

import { HeartOutlined, ShoppingCartOutlined } from '@ant-design/icons'
import { Button, Modal, Skeleton } from 'antd'
import React, { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { ProductImageGallery } from './ProductImageGallery'
import { ProductExtra } from './ProductExtra'
import { ChooseListModal } from '../ChooseListModal'

export const ProductHeader = ({ product, loading = false }) => {
    const [isChooseListModalVisible, setIsChooseListModalVisible] = useState(false)

    const onAddToCartClicked = () => {
        console.log('add to cart', product)
    }

    const onAddToListClicked = () => {
        console.log('on add to list', product)
        setIsChooseListModalVisible(true)
    }
    const onChooseList = list => {
        console.log('on choose list', list)
        setIsChooseListModalVisible(false)
    }
    const onCancelChooseList = () => {
        console.log('on cancel choose list')
        setIsChooseListModalVisible(false)
    }

    if (loading) {
        return (
            <div className="product-header">
                <div className="product-header-gallery">
                    <ProductImageGallery loading={loading} product={product} />
                </div>
                <div className="product-header-main">
                    <Skeleton active title paragraph={{ rows: 6 }} />
                </div>
                <div className="product-header-vendor"></div>
                <div className="product-header-extra">
                    <ProductExtra loading={loading} product={product} />
                </div>
                <div className="product-header-buttons">
                    <Button
                        disabled
                        onClick={onAddToCartClicked}
                        size="large"
                        type="primary"
                        icon={<ShoppingCartOutlined />}>
                        Add to cart
                    </Button>
                    <Button disabled onClick={onAddToListClicked} size="large" icon={<HeartOutlined />} />
                </div>
            </div>
        )
    }

    return (
        <div className="product-header">
            <div className="product-header-gallery">
                <ProductImageGallery product={product} />
            </div>
            <div className="product-header-main">
                <h1 className="product-header-main-title">{product.name}</h1>
                <p>{product.description}</p>
            </div>
            <div className="product-header-vendor">
                <span className="product-header-vendor-label">Vendor:</span>&nbsp;
                <Link to={`/vendor/${product.vendor}`}>{product.vendor}</Link>
                &nbsp;<span className="product-header-vendor-rating">{product.vendor_rating ?? 9.7}</span>
            </div>
            <div className="product-header-extra">
                <ProductExtra product={product} loading={loading} />
            </div>
            <div className="product-header-buttons">
                <Button onClick={onAddToCartClicked} size="large" type="primary" icon={<ShoppingCartOutlined />}>
                    Add to cart
                </Button>
                <Button onClick={onAddToListClicked} size="large" icon={<HeartOutlined />} />
            </div>
            <Modal
                visible={isChooseListModalVisible}
                onCancel={onCancelChooseList}
                footer={null}
                title="Choose a list"
                destroyOnClose>
                <ChooseListModal onChooseList={onChooseList} />
            </Modal>
        </div>
    )
}
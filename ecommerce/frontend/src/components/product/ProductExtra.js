import './ProductExtra.less'

import { Rate, Skeleton } from 'antd'
import React from 'react'
import { round, formatPrice } from '../../utils'

export const ProductExtra = ({ product, loading = false }) => {
    const getPriceView = () => {
        if (loading) {
            return <Skeleton.Avatar shape="square" size="large" active />
        }
        // only doing this because not sure what product will contain
        const hasDiscount = product.discount > 0
        if (hasDiscount) {
            const discount = product.discount
            const oldPrice = round(product.price, 2)
            const newPrice = round(product.price_after_discount, 2)

            return (
                <div className="product-extra-price">
                    <div className="product-extra-old-price">
                        {formatPrice(oldPrice)} {product.currency ?? 'TL'}
                    </div>
                    <div className="product-extra-new-price">
                        {formatPrice(newPrice)} {product.currency ?? 'TL'}
                    </div>
                </div>
            )
        }

        return (
            <div className="product-extra-price">
                <div className="product-extra-new-price">
                    {formatPrice(round(product.price, 2))} {product.currency ?? 'TL'}
                </div>
            </div>
        )
    }

    if (loading) {
        return (
            <div className="product-extra">
                <div className="product-extra-reviews">{getPriceView()}</div>
            </div>
        )
    }

    return (
        <div className="product-extra">
            <div className="product-extra-reviews">
                {getPriceView()}
                <div>
                    <Rate value={round(product.rating, 2) ?? 4.1} disabled /> {round(product.rating, 2) ?? 4.1}
                </div>
                {product.rating_count ?? 1234} reviews
            </div>
        </div>
    )
}

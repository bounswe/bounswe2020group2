import './VendorPageContent.less'

import { Pagination } from 'antd'
import { useState } from 'react'

import { ProductCard } from '../product_card/ProductCard'
import { useAppContext } from '../../context/AppContext'

export const VendorPageContent = ({ products }) => {
    const { user } = useAppContext()

    return (
        <div className="vendor-page-results">
            <div className="vendor-page-results-product-grid">
                {products.map(product => (
                    <ProductCard key={product.id} product={product} />
                ))}
            </div>
        </div>
    )
}

import './SearchResults.less'

import { Pagination } from 'antd'
import { useState } from 'react'

import { ProductCard } from '../product_card/ProductCard'

export const SearchResults = ({ products, pagination, onPaginationChanged }) => {
    return (
        <div>
            <div className="product-grid">
                {products.map(product => (
                    <ProductCard key={product.productId} product={product} />
                ))}
            </div>
            <Pagination pagination={pagination} onChange={onPaginationChanged} hideOnSinglePage />
        </div>
    )
}

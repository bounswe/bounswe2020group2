import './VendorPageContent.less'

import { useState } from 'react'

import { useAppContext } from '../../context/AppContext'
import { ProductCard } from '../product_card/ProductCard'

export const VendorPageContent = ({ products, editable, onChange }) => {
    return (
        <div className="vendor-page-results">
            <div className="vendor-page-results-product-grid">
                {products.map(product => (
                    <ProductCard key={product.id} product={product} editMode={editable} onChange={onChange} />
                ))}
            </div>
        </div>
    )
}

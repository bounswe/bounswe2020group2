import './SearchResults.less'
import { Pagination } from 'antd'
import { ProductCard } from '../product_card/ProductCard'

export const SearchResults = ({ products, pagination, onPaginationChanged }) => {
    return (
        <div className="search-results">
            <div className="search-results-product-grid">
                {products.map(product => (
                    <ProductCard key={product.id} product={product} />
                ))}
            </div>
            <Pagination
                className="search-results-pagination"
                {...pagination}
                onChange={onPaginationChanged}
                showTotal={(total, range) => `${range[0]}-${range[1]} of ${total} items`}
                showSizeChanger
                showQuickJumper
            />
        </div>
    )
}

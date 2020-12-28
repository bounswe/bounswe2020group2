import { notification, Spin } from "antd"
import { useEffect, useState } from "react"
import { useAppContext } from "../context/AppContext"
import * as R from 'ramda'
import { api } from "../api"
import { formatProduct, formatSearchQueryParams } from "../utils"
import { ProductCard } from "./product_card/ProductCard"
import './HorizontalProductList.less'

export const HorizontalProductList = ({filters}) => {
    const [isLoading, setIsLoading] = useState(true)
    const [products, setProducts] = useState([])
    const {categories} = useAppContext()

    useEffect(() => {
        fetch()
    },[])

    async function fetch() {
        try {
            setIsLoading(true)
            

            
            const queryParams = R.reject(R.equals(undefined), formatSearchQueryParams({
                search: {},
                filters,
                pagination: {},
            }))
            
            const {
                data: {
                    data: { products },
                },
            } = await api.post(`/search/products`, queryParams)

            setProducts(products.map(formatProduct))

        } catch {
            notification.error({ description: 'Failed to load search results' })
        } finally {
            setIsLoading(false)
        }
    }
    const category = categories.find(x => x.id === filters.category)
    const subcategory = category?.subcategories.find(x => x.id === filters.subcategory)


    return <Spin spinning={isLoading}>
        <div className="best-sellers">
            <h1 className="best-sellers-header">
                Best Sellers in {subcategory?.name ?? category?.name}
            </h1>
            <div className="best-sellers-content">
                {products.map(product => (
                    <ProductCard key={product.id} product={product} />
                ))}
            </div>
        </div>
    </Spin>
    
}
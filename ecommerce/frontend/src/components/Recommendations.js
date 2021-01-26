import './Recommendations.less'
import { useEffect, useState } from 'react'
import { formatProduct } from '../utils'
import { notification, Spin } from 'antd'
import { api } from '../api'
import { ProductCard } from './product_card/ProductCard'
import { useAppContext } from '../context/AppContext'

export const Recommendations = () => {
    const [isLoading, setIsLoading] = useState(true)
    const [products, setProducts] = useState([])
    const { user } = useAppContext()

    useEffect(() => {
        fetch()
    }, [])

    async function fetch() {
        try {
            setIsLoading(true)
            const {
                data: { status, products },
            } = await api.get(`/recommendation`)
            if (status.successful) {
                setProducts(products.map(formatProduct))
            } else {
                notification.warning({ message: status.message })
            }
        } catch {
            notification.error({ description: 'Failed to load recommendations' })
        } finally {
            setIsLoading(false)
        }
    }

    return (
        <Spin spinning={isLoading}>
            <div className="recommendations-wrapper">
                <h1 className="recommendations-header">Recommended Products For You {user.name}</h1>
                <div className="recommendations-content">
                    {products.map(product => (
                        <ProductCard key={product.id} product={product} />
                    ))}
                </div>
            </div>
        </Spin>
    )
}

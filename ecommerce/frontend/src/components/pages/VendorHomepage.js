import getflixLogo from '../../assets/logo.png'
import { useAppContext } from '../../context/AppContext'
import { useEffect, useState } from 'react'
import { Spin, Button, Rate } from 'antd'
import { api } from '../../api'
import { Redirect, useHistory } from 'react-router-dom'
import { DeleteOutlined, EditOutlined } from '@ant-design/icons'
import { HorizontalProductList } from '../HorizontalProductList'
import './VendorHomepage.less'
import { round } from '../../utils'
import { ProductCard } from '../product_card/ProductCard'
import { product } from '../../mocks/mocks'

export const VendorHomepage = props => {
    // example usage
    const { id } = props.match.params

    return (
        <div>
            <VendorSplash />
            <div className="vendor-page-product-card-editable">
                <div className="vendor-page-product-card-editing-icons">
                    <EditOutlined />
                    <DeleteOutlined />
                </div>
                <div className="vendor-page-product-card">
                    <ProductCard product={product} />
                </div>
            </div>
            {/* <HomePage_MainContent /> */}
        </div>
    )
}
const getVendorRatingLevel = rating => {
    if (rating <= 5.0) {
        return 'low'
    }

    if (rating <= 8.0) {
        return 'medium'
    }

    return 'high'
}
const VendorSplash = () => {
    const vendor = {}

    return (
        <div className="vendor-splash">
            <div className="vendor-image">
                <img src={getflixLogo} className="splash-logo"></img>
            </div>
            <div className="vendor-header">
                <h1 className="vendor-name">SAMSUNG</h1>
                <h3 className="vendor-slogan">Create the future</h3>
                {/* //vendor?.rating ??  */}
                <span
                    className={`product-header-vendor-rating product-header-vendor-rating__${getVendorRatingLevel(4)}`}>
                    {4}
                </span>
            </div>
            <div className="vendor-edit-button">
                <Button type="primary" icon={<EditOutlined />} href="/profile">
                    Edit Page
                </Button>
            </div>
        </div>
    )
}

const HomePage_MainContent = () => {
    const [trendingProducts, setTrendingProducts] = useState([])
    const [isLoading, setIsLoading] = useState(true)
    const { categories } = useAppContext()

    useEffect(() => {
        async function fetch() {
            try {
                setIsLoading(true)

                const { data } = await api.get(`/products/homepage/${5}`)
                setTrendingProducts(data)
            } catch (error) {
                console.error('failed to load trending products', error)
            } finally {
                setIsLoading(false)
            }
        }

        fetch()
    }, [])

    return (
        <Spin spinning={isLoading}>
            <div className="trending-grid-wrapper">
                {categories.map(category => {
                    const filters = {
                        category: category.id,
                        sortBy: 'best-sellers',
                        type: 'products',
                    }
                    return <HorizontalProductList key={category.id} filters={filters} />
                })}
            </div>
        </Spin>
    )
}

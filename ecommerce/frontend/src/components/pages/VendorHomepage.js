import getflixLogo from '../../assets/logo.png'
import { useAppContext } from '../../context/AppContext'
import { useEffect, useState } from 'react'
import { Spin, Button, Rate } from 'antd'
import { api } from '../../api'
import { Redirect, useHistory } from 'react-router-dom'
import { HorizontalProductList } from '../HorizontalProductList'
import './VendorHomepage.less'
import { round } from '../../utils'
import { ProductCard } from '../product_card/ProductCard'
import { product } from '../../mocks/mocks'
import { DeleteOutlined, EditOutlined } from '@ant-design/icons'
import { Tabs } from 'antd'
import { UserReviews } from '../UserReview/UserReviews'

const { TabPane } = Tabs
function callback(key) {
    console.log(key)
}

export const VendorHomepage = props => {
    // example usage
    const { id } = props.match.params

    return (
        <div>
            <VendorSplash />
            <div style={{ margin: '32px 64px 0 64px' }}>
                <Tabs onChange={callback} type="card">
                    <TabPane tab="Products" key="vendor-products">
                        <VendorMainContent />
                    </TabPane>
                    <TabPane tab="Reviews" key="vendor-reviews">
                        <UserReviews productId={1} />
                    </TabPane>
                </Tabs>
            </div>
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
    const vendorHeaderDetails = {
        title: 'SAMSUNG',
        imageUrl:
            'data:image/png;base64,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',
        description: 'Create the future',
        rating: '6.7',
    }
    const { title, imageUrl, description, rating } = vendorHeaderDetails
    const { user } = useAppContext()
    const isVendor = user.type === 'vendor'
    return (
        <div className="vendor-splash">
            <div className="vendor-image">
                <img src={imageUrl} className="splash-logo"></img>
            </div>
            <div className="vendor-header">
                <div className="vendor-header-content">
                    <h1 className="vendor-name">{title}</h1>
                    <h3 className="vendor-slogan">{description}</h3>
                    <span
                        className={`product-header-vendor-rating product-header-vendor-rating__${getVendorRatingLevel({
                            rating,
                        })}`}>
                        {rating}
                    </span>
                </div>
                <div className="vendor-edit-button">
                    {isVendor ? (
                        <Button type="primary" icon={<EditOutlined />} href="/profile">
                            Edit Page
                        </Button>
                    ) : (
                        <Button type="primary" icon={<EditOutlined />} href="/profile/messages">
                            Send a message
                        </Button>
                    )}
                </div>
            </div>
        </div>
    )
}

const VendorMainContent = () => {
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
            <div className="vendor-page-trending-grid-wrapper">
                {categories.map(category => {
                    const filters = {
                        category: category.id,
                        sortBy: 'best-sellers',
                        type: 'products',
                    }
                    return <HorizontalProductList key={category.id} filters={filters} editable={true} />
                })}
            </div>
        </Spin>
    )
}

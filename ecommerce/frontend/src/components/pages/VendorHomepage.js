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
import { StickyContainer, Sticky } from 'react-sticky'
import { UserReviews } from '../UserReview/UserReviews'

const { TabPane } = Tabs
function callback(key) {
    console.log(key)
}
const renderTabBar = (props, DefaultTabBar) => (
    <Sticky bottomOffset={80}>
        {({ style }) => <DefaultTabBar {...props} className="site-custom-tab-bar" style={{ ...style }} />}
    </Sticky>
)
export const VendorHomepage = props => {
    // example usage
    const { id } = props.match.params

    return (
        <div>
            <VendorSplash />
            <div style={{ margin: '32px 64px 0 64px' }}>
                <Tabs onChange={callback} type="card">
                    <TabPane tab="Products" key="vendor-products">
                        <p>!</p>

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
            <div className="trending-grid-wrapper">
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

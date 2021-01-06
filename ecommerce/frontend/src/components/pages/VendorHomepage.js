import './VendorHomepage.less'
import getflixLogo from '../../assets/logo.png'
import { useAppContext } from '../../context/AppContext'
import { useEffect, useState } from 'react'
import { Spin, Button, Rate } from 'antd'
import { api } from '../../api'
import { Redirect, useHistory } from 'react-router-dom'
import { SearchInput } from '../SearchInput'
import { SearchInputWrapper } from '../search/SearchInputWrapper'
import { format } from 'prettier'
import { EditOutlined } from '@ant-design/icons'
import { HorizontalProductList } from '../HorizontalProductList'

export const VendorHomepage = props => {
    // example usage
    const { id } = props.match.params

    return (
        <div>
            <VendorSplash />
            <HomePage_MainContent />
        </div>
    )
}

const VendorSplash = () => {
    return (
        <div className="vendor-splash">
            <div className="vendor-image">
                <img src={getflixLogo} className="splash-logo"></img>
            </div>
            <div className="vendor-header">
                <h1 className="vendor-name">SAMSUNG</h1>
                <h3 className="vendor-slogan">Create the future</h3>
                <Rate />
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

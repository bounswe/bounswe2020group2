import './HomePage.less'
import { TrendingGrid } from '../TrendingGrid'
import getflixLogo from '../../assets/logo.png'
import { useAppContext } from '../../context/AppContext'
import { useEffect, useState } from 'react'
import { Spin } from 'antd'
import { formatProduct, productSortBy, sleep } from '../../utils'
import { api } from '../../api'
import { SearchInput } from '../SearchInput'
import { SearchInputWrapper } from '../search/SearchInputWrapper'
import { format } from 'prettier'

export const HomePage = () => {
    // example usage
    return (
        <div>
            <HomePage_Splash />
            <HomePage_MainContent />
        </div>
    )
}

const HomePage_Splash = () => {
    return (
        <div className="splash">
            <div className="splash-text">
                <img src={getflixLogo} className="splash-logo"></img>
                <div className="splash-slogan">
                    <h1 className="splash-slogan-first">The biggest marketplace in the world</h1>
                    <h3 className="splash-slogan-second">Any product you want, right at your doorstep</h3>
                </div>
            </div>
        </div>
    )
}

const HomePage_MainContent = () => {
    const [trendingProducts, setTrendingProducts] = useState([])
    const [isLoading, setIsLoading] = useState(true)

    useEffect(() => {
        async function fetch() {
            try {
                setIsLoading(true)

                const { data } = await api.get(`/products/homepage/${5}`)
                setTrendingProducts(data.products)
            } catch (error) {
                console.error('failed to load trending products', error)
            } finally {
                setIsLoading(false)
            }
        }

        fetch()
    }, [])

    return (
        <div className="trending-grid-wrapper">
            {<Spin spinning={isLoading}>
                <TrendingGrid trendingProducts={trendingProducts} />
            </Spin>}
        </div>
    )
}

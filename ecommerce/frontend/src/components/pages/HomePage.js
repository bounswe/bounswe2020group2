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

                // once backend works we will uncomment the following
                // const { data } = await api.get(`/products/homepage/${5}`)
                // let { products } = data
                // products = products.map(formatProduct)
                // setTrendingProducts(products)

                await sleep(1000)
                setTrendingProducts([
                    {
                        title: 'Purse',
                        rating: '5',
                        price: '30.00',
                        currency: 'TL',
                        imageUrl: 'https://stylecaster.com/wp-content/uploads/2018/10/bag-storage.jpg',
                        id: 1324,
                    },
                    {
                        title: ' Watch',
                        rating: '5',
                        price: '45.00',
                        currency: 'TL',
                        imageUrl: 'https://cdn.pixabay.com/photo/2019/07/13/13/42/watch-4334815_960_720.jpg',
                        id: 2123,
                    },
                    {
                        title: 'Nivea Care Package',
                        rating: '5',
                        price: '30.00',
                        currency: 'TL',
                        imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/6/65/Product_Photography.jpg',
                        id: 32345,
                    },
                    {
                        title: 'Cocooil Shampoo',
                        rating: '5',
                        price: '45.00',
                        currency: 'TL',
                        imageUrl:
                            'https://images.unsplash.com/photo-1526947425960-945c6e72858f?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80',
                        id: 4435,
                    },
                    {
                        title: 'onlar iyi',
                        rating: '5',
                        price: '96.00',
                        currency: 'TL',
                        imageUrl:
                            'https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQwCP24CO6WEGAUhkfVg4ozqfeve3pvkhEEZg&usqp=CAU',
                        id: 5678,
                    },
                ])
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
            <Spin spinning={isLoading}>
                <TrendingGrid trendingProducts={trendingProducts} />
            </Spin>
        </div>
    )
}

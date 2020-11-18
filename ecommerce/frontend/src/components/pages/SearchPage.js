import './SearchPage.less'

import { SearchInput } from '../SearchInput'
import { SearchSidePanel } from '../search/SearchSidePanel'
import { SearchResults } from '../search/SearchResults'
import { useHistory, useLocation } from 'react-router-dom'
import qs from 'query-string'
import { useEffect, useState } from 'react'
import { sleep } from '../../utils'
import Axios from 'axios'
import { notification, Spin } from 'antd'

export const SearchPage = () => {
    const history = useHistory()
    const location = useLocation()

    const initialValues = qs.parse(location.search, {
        arrayFormat: 'comma',
        parseNumbers: true,
        parseBooleans: true,
    })

    const testProducts = [...Array(10)].map((x, ix) => {
        return {
            title: 'Title',
            rating: '5',
            price: '30.00',
            currency: 'TL',
            imageUrl: `https://picsum.photos/300?q=${ix}`,
            width: 300,
            productId: ix,
        }
    })

    const [isLoading, setIsLoading] = useState(false)
    const [products, setProducts] = useState(testProducts)

    useEffect(() => {
        async function fetch() {
            try {
                setIsLoading(true)
                await sleep(1000)
                notification.success({ description: 'Search results ready!' })
            } catch {
                notification.error({ description: 'Failed to load search results' })
            } finally {
                setIsLoading(false)
            }
        }

        fetch()
    }, [location.search])

    const onSubmit = values => {
        history.push({
            pathname: '/search',
            search: '?' + qs.stringify(values, { arrayFormat: 'comma' }),
        })
    }

    return (
        <div className={'search-page'}>
            <div className="search-page-bar">
                <SearchInput />
            </div>
            <div className="search-page-main">
                <div className="search-page-side-panel">
                    <SearchSidePanel initialValues={initialValues} onSubmit={onSubmit} />
                </div>
                <div className="search-page-results">
                    <Spin spinning={isLoading}>
                        <SearchResults products={products} />
                    </Spin>
                </div>
            </div>
        </div>
    )
}

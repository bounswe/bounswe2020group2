import './SearchPage.less'

import { SearchInput } from '../SearchInput'
import { SearchSidePanel } from '../search/SearchSidePanel'
import { SearchResults } from '../search/SearchResults'
import { useHistory, useLocation } from 'react-router-dom'
import qs from 'query-string'
import { useEffect, useState } from 'react'
import { sleep } from '../../utils'
import Axios from 'axios'
import { notification, Spin, Form } from 'antd'
import uuidv4 from 'uuid/dist/v4'
import * as R from 'ramda'

/**
 * This is a wrapper around the real _SearchPage component
 * It used to force remount the _SearchPage so that it is reloaded with new initial values
 * It makes it easier to reason about
 * It is achieved by setting location.search as a key to _SearchPage, which invalidates on every history.push for example
 */
export const SearchPage = () => {
    const location = useLocation()

    const initialValues = qs.parse(location.search, {
        arrayFormat: 'comma',
        parseNumbers: true,
        parseBooleans: true,
    })

    return <_SearchPage key={location.search} initialValues={initialValues} />
}

export const _SearchPage = ({ initialValues = {} }) => {
    const initialQuery = { query: initialValues.query }
    const initialFilters = R.omit('query', initialValues)

    const history = useHistory()

    const [isLoading, setIsLoading] = useState(true)
    const [products, setProducts] = useState([])

    useEffect(() => {
        async function fetch() {
            try {
                setIsLoading(true)
                await sleep(1000)
                setProducts(
                    [...Array(10)].map(x => {
                        return {
                            title: 'Title',
                            rating: '5',
                            price: '30.00',
                            currency: 'TL',
                            imageUrl: `https://picsum.photos/300?q=${uuidv4()}`,
                            width: 300,
                            productId: uuidv4(),
                        }
                    }),
                )

                notification.success({ description: 'Search results ready!' })
            } catch {
                notification.error({ description: 'Failed to load search results' })
            } finally {
                setIsLoading(false)
            }
        }

        fetch()
    }, [])

    const onSubmitFilters = newFilters => {
        const queryParams = { ...newFilters, ...initialQuery }
        history.push({
            pathname: '/search',
            search: '?' + qs.stringify(queryParams, { arrayFormat: 'comma' }),
        })
    }

    const onSearch = newQuery => {
        const queryParams = { ...initialFilters, ...{ query: newQuery } }
        history.push({
            pathname: '/search',
            search: '?' + qs.stringify(queryParams, { arrayFormat: 'comma' }),
        })
    }

    return (
        <Form.Provider onFormFinish={console.log}>
            <div className={'search-page'}>
                <div className="search-page-bar">
                    <SearchInput initialValue={initialValues.query} onSearch={onSearch} />
                </div>
                <div className="search-page-main">
                    <div className="search-page-side-panel">
                        <SearchSidePanel initialValues={initialValues} onSubmit={onSubmitFilters} />
                    </div>
                    <div className="search-page-results">
                        <Spin spinning={isLoading}>
                            <SearchResults products={products} />
                        </Spin>
                    </div>
                </div>
            </div>
        </Form.Provider>
    )
}

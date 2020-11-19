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
    const defaultPageSize = 10
    const values = {
        query: initialValues.query,
        filters: R.omit('query', initialValues),
        pagination: {
            pageSize: defaultPageSize,
            ...R.pick(['current', 'pageSize'], initialValues),
        },
    }

    const history = useHistory()

    const [isLoading, setIsLoading] = useState(true)

    const [products, setProducts] = useState([])
    // total number of products matching the query in the database, NOT returned by backende([])
    const [total, setTotal] = useState(0)

    useEffect(() => {
        async function fetch() {
            try {
                setIsLoading(true)
                await sleep(1000)
                const total = 50
                setProducts(
                    [...Array(total)].map(x => {
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
                setTotal(total)

                notification.success({ description: 'Search results ready!' })
            } catch {
                notification.error({ description: 'Failed to load search results' })
            } finally {
                setIsLoading(false)
            }
        }

        fetch()
    }, [])

    const refreshSearchWith = queryParams => {
        history.push({
            pathname: '/search',
            search:
                '?' +
                qs.stringify(
                    { ...queryParams.filters, query: queryParams.query, ...queryParams.pagination },
                    { arrayFormat: 'comma' },
                ),
        })
    }

    const onSubmitFilters = filters => refreshSearchWith({ ...values, filters })
    const onSearch = query => refreshSearchWith({ ...values, query })
    const onPaginationChanged = (current, pageSize) =>
        refreshSearchWith({ ...values, pagination: { ...values.pagination, current, pageSize } })

    return (
        <Form.Provider onFormFinish={console.log}>
            <div className={'search-page'}>
                <div className="search-page-bar">
                    <SearchInput initialValue={values.query} onSearch={onSearch} />
                </div>
                <div className="search-page-main">
                    <div className="search-page-side-panel">
                        <SearchSidePanel initialValues={values.filters} onSubmit={onSubmitFilters} />
                    </div>
                    <div className="search-page-results">
                        <Spin spinning={isLoading}>
                            <SearchResults
                                products={products}
                                pagination={{
                                    ...values.pagination,
                                    total,
                                }}
                                onPaginationChanged={onPaginationChanged}
                            />
                        </Spin>
                    </div>
                </div>
            </div>
        </Form.Provider>
    )
}

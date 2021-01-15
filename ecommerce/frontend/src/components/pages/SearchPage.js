import './SearchPage.less'

import { Breadcrumb, notification, Spin } from 'antd'
import qs from 'query-string'
import * as R from 'ramda'
import { useEffect, useState } from 'react'
import { Helmet } from 'react-helmet'
import { Link, useHistory } from 'react-router-dom'

import { api } from '../../api'
import { SearchResults } from '../search/SearchResults'
import { SearchSidePanel } from '../search/SearchSidePanel'
import { formatProduct, formatSearchQueryParams } from '../../utils'
import { useAppContext } from '../../context/AppContext'

import { HomeOutlined } from '@ant-design/icons'

/**
 * This is a wrapper around the real _SearchPage component
 * It used to force remount the _SearchPage so that it is reloaded with new initial values
 * It makes it easier to reason about
 * It is achieved by setting location.search as a key to _SearchPage, which invalidates on every history.push for example
 */
export const SearchPage = ({ location, match }) => {
    const initialValues = {
        ...qs.parse(location.search, {
            arrayFormat: 'comma',
            parseNumbers: true,
            parseBooleans: true,
        }),
        type: match.params.type,
    }

    return <_SearchPage key={location.search} initialValues={initialValues} />
}

export const _SearchPage = ({ initialValues = {} }) => {
    const defaultPageSize = 10

    const values = {
        search: { query: initialValues.query, type: initialValues.type },
        filters: R.omit(['query', 'type', 'current', 'pageSize'], initialValues),
        pagination: {
            pageSize: defaultPageSize,
            ...R.pick(['current', 'pageSize'], initialValues),
        },
    }

    const history = useHistory()
    const { categories } = useAppContext()

    const [isLoading, setIsLoading] = useState(true)

    const [products, setProducts] = useState([])

    // total number of products matching the query in the database, NOT returned by backend
    const [total, setTotal] = useState(0)

    useEffect(() => {
        async function fetch() {
            try {
                setIsLoading(true)

                console.log('filters', values)
                const queryParams = R.reject(R.equals(undefined), formatSearchQueryParams(values))

                console.log('queryParams', queryParams)

                const {
                    data: {
                        data: { pagination, products },
                    },
                } = await api.post(`/search/products`, queryParams)

                setProducts(products.map(formatProduct))

                setTotal(pagination.total_items)

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
            pathname: `/search/${queryParams.search.type}`,
            search:
                '?' +
                qs.stringify(
                    { ...queryParams.search, ...queryParams.filters, ...queryParams.pagination },
                    { arrayFormat: 'comma', skipNull: true, skipEmptyString: true },
                ),
        })
    }

    const onSubmitFilters = filters => refreshSearchWith({ ...values, filters })

    // const onSearch = search =>
    //     refreshSearchWith({
    //         search,
    //         filters: {},
    //         pagination: { pageSize: values.pagination.pageSize },
    //     })

    const onPaginationChanged = (current, pageSize) =>
        refreshSearchWith({ ...values, pagination: { ...values.pagination, current, pageSize } })

    const getTitle = values => {
        const { type, query } = values.search

        const prefix = query ?? 'All'

        return `${prefix} ${type} - Getflix`
    }

    const getBreadcrumbs = () => {
        const category = categories.find(x => x.id === values.filters.category)
        const subcategory = category?.subcategories.find(x => x.id === values.filters.subcategory)

        return (
            <Breadcrumb>
                <Breadcrumb.Item>
                    <Link to={'/'}>
                        <HomeOutlined /> Home
                    </Link>
                </Breadcrumb.Item>
                {category && (
                    <Breadcrumb.Item>
                        <Link to={`/search/${values?.search?.type ?? 'products'}?category=${category.id}`}>
                            {category.name}
                        </Link>
                    </Breadcrumb.Item>
                )}
                {subcategory && (
                    <Breadcrumb.Item>
                        <Link to={`/search/${values?.search?.type ?? 'products'}?subcategory=${subcategory.id}`}>
                            {subcategory.name}
                        </Link>
                    </Breadcrumb.Item>
                )}
            </Breadcrumb>
        )
    }

    return (
        <>
            <Helmet>
                <title>{getTitle(values)}</title>
            </Helmet>
            <div>
                <div className="search-page-breadcrumbs">{getBreadcrumbs()}</div>
                <div className={'search-page'}>
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
            </div>
        </>
    )
}

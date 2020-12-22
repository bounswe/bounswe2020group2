import './SearchPage.less'

import { notification, Spin } from 'antd'
import qs from 'query-string'
import * as R from 'ramda'
import { useEffect, useState } from 'react'
import { Helmet } from 'react-helmet'
import { useHistory } from 'react-router-dom'

import { api } from '../../api'
// import { categories, subcategories } from '../../utils'
import { SearchResults } from '../search/SearchResults'
import { SearchSidePanel } from '../search/SearchSidePanel'

const formatSearchQueryParams = values => ({
    query: values.search?.query,
    // query: this can be undefined it means doesn't matter
    // query: for now, case-insensitive search in title or description

    category: values.filters?.category,
    // category: if missing, assume all products

    subcategory: values.filters?.subcategory,
    // subcategory: if missing, assume all subcategories of the category
    // subcategory: subcategory cannot be given without specifying category

    brand: values.filters?.brands,
    // brand: OR semantic
    // brand: these are brand ids I get from the database
    // brand: if brand is undefined or empty array then consider it as any brand

    max_price: values.filters?.maxPrice,
    // max_price: if missing, assume +infinity

    min_rating: values.filters?.rating,
    // min_rating: if missing, assume 0
    // min_rating: min 0, max 5
    // min_rating: >= semantic

    // == sorting ==
    sort_by: values.filters?.sortBy,
    // sort_by: if missing, assume 'best-sellers'

    sort_order: 'increasing',
    // sort_order: if missing, assume 'increasing'
    // sort_order: decreasing best-sellers -> best sellers shown first
    // sort_order: decreasing newest-arrivals -> newest arrivals shown first
    // sort_order: increasing price -> low price products first
    // sort_order: increasing average-customer-review -> low review first
    // sort_order: increasing number-of-comments -> low comments first

    // == pagination ==
    page: values.pagination?.current,
    // page: pages start from 0
    // page: if missing, assume 0

    page_size: values.pagination?.pageSize,
    // page_size: smallest page_size should 1, biggest should be 100
    // page_size: if missing, assume 10
})

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

    const [isLoading, setIsLoading] = useState(true)

    const [products, setProducts] = useState([])

    // total number of products matching the query in the database, NOT returned by backend
    const [total, setTotal] = useState(0)

    useEffect(() => {
        async function fetch() {
            try {
                setIsLoading(true)
                console.log(values)

                const {
                    data: {
                        data: { pagination, products },
                    },
                } = await api.post(`/search/products`, formatSearchQueryParams(values))

                setProducts(
                    products.map(p => {
                        return {
                            id: p.id,
                            title: p.name,
                            rating: p.total_rating,
                            price: p.price,
                            currency: 'TL',
                            imageUrl: p.images[0],
                        }
                    }),
                )

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

    const onSearch = search =>
        refreshSearchWith({
            search,
            filters: {},
            pagination: { pageSize: values.pagination.pageSize },
        })

    const onPaginationChanged = (current, pageSize) =>
        refreshSearchWith({ ...values, pagination: { ...values.pagination, current, pageSize } })

    const getTitle = values => {
        const type = values.search.type

        const query = values.search.query
        const category = values.filters.category && categories[values.filters.category]
        const subcategory =
            values.filters.category &&
            values.filters.subcategory &&
            subcategories[values.filters.category][values.filters.subcategory]

        const prefix = query ?? subcategory ?? category ?? 'All'

        return `${prefix} ${type}s - Getflix`
    }

    return (
        <>
            <Helmet>
                <title>{getTitle(values)}</title>
            </Helmet>
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
        </>
    )
}

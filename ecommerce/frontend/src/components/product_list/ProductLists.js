import './ProductLists.less'

import { notification, Spin } from 'antd'
import { useEffect, useState } from 'react'

import { api } from '../../api'
import { useAppContext } from '../../context/AppContext'
import { formatList } from '../../utils'
import { ProductList } from './ProductList'
// import { Purchase } from './Purchase'

export const ProductLists = () => {
    const [lists, setLists] = useState([])
    const [isLoading, setIsLoading] = useState(false)
    const { user } = useAppContext()

    const fetch = async () => {
        try {
            setIsLoading(true)

            const { data } = await api.get('/lists')

            console.log('getLists', data)

            setLists(data.lists.map(formatList))
        } catch (error) {
            notification.error({ description: 'Failed to get the lists' })
            console.error(error)
        } finally {
            setIsLoading(false)
        }
    }

    // const onListCancelled = fetch
    // const onPurchaseUpdated = fetch

    useEffect(() => {
        fetch()
    }, [])

    const c = x => 'product-lists-' + x

    return (
        <div className={'product-lists'}>
            <div className={c('header')}>Product Lists</div>
            <Spin spinning={isLoading}>
                <div className={c('content')}>
                    {lists.map(list => {
                        return <ProductList key={list.id} list={list} />
                    })}
                </div>
            </Spin>
        </div>
    )
}

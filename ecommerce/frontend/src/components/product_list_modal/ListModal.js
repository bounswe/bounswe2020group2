import { Form, Modal, notification, Spin } from 'antd'
import { useState, useEffect } from 'react'

import { api } from '../../api'
import { vendorOrders } from '../../mocks/mocks'
import { formatList } from '../../utils'
import { ListModalInner } from './ListModalInner'

export const ListModal = ({ product = vendorOrders[0].product, visible = true, onOk = () => {} }) => {
    const [form] = Form.useForm()
    const [loading, setLoading] = useState(false)
    const [createLoading, setCreateLoading] = useState(false)
    const [loadingList, setLoadingList] = useState(null)
    const [lists, setLists] = useState([])

    const getLists = async (product, showLoading = true) => {
        try {
            setLoading(showLoading)

            const { data } = await api.get('/lists')

            console.log('getLists', data)

            let _lists = data.lists.map(formatList)
            _lists = _lists.map(list => {
                return {
                    ...list,
                    hasProduct: list.products.some(entry => entry.product.id === product.id),
                }
            })

            setLists(_lists)
        } catch (error) {
            notification.error({ description: 'Failed to get the lists' })
            console.error(error)
        } finally {
            setLoading(false)
        }
    }

    const removeProductFromList = async (product, list) => {
        try {
            setLoadingList(list)
            await api.delete(`/lists/${list.id}/product/${product.id}`)
        } catch (error) {
            notification.error({ description: 'Failed to remove product from list' })
        } finally {
            setLoadingList(null)
        }
    }

    const addProductToList = async (product, list) => {
        try {
            setLoadingList(list)
            await api.post(`/lists/${list.id}/product/${product.id}`)
        } catch (error) {
            notification.error({ description: 'Failed to add product to list' })
        } finally {
            setLoadingList(null)
        }
    }

    const onListClick = list => async () => {
        if (list.hasProduct) {
            await removeProductFromList(product, list)
        } else {
            await addProductToList(product, list)
        }
        setLoadingList(list)
        await getLists(product, false)
        setLoadingList(null)
    }

    const onCreateProductListFinish = async values => {
        try {
            setCreateLoading(true)
            const {
                data: { id },
            } = await api.post('/lists', values)
            await addProductToList(product, { id })
            await getLists(product, false)
        } catch (error) {
            console.error(error)
            notification.error({ description: 'Failed to create product list' })
        } finally {
            setCreateLoading(false)
        }
        console.log(values)
    }

    useEffect(() => {
        getLists(product, true)
    }, [])

    return (
        <Modal
            forceRender
            destroyOnClose
            title="Add product to list"
            width={700}
            visible={visible}
            onOk={onOk}
            okText={'Done'}
            cancelButtonProps={{ style: { display: 'none' } }}>
            <ListModalInner
                product={product}
                loading={loading}
                lists={lists}
                onListClick={onListClick}
                loadingList={loadingList}
                onCreateProductListFinish={onCreateProductListFinish}
                createLoading={createLoading}
            />
        </Modal>
    )
}

import './ProductLists.less'

import { Button, Form, Input, notification, Spin } from 'antd'
import { useEffect, useState } from 'react'

import { api } from '../../api'
import { useAppContext } from '../../context/AppContext'
import { formatList } from '../../utils'
import { ProductList } from './ProductList'

export const ProductLists = () => {
    const [lists, setLists] = useState([])
    const [isLoading, setIsLoading] = useState(false)
    const [form] = Form.useForm()
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
    const onChange = fetch

    useEffect(() => {
        fetch()
    }, [])

    const c = x => 'product-lists-' + x

    const [createLoading, setCreateLoading] = useState(false)

    const onFinish = async values => {
        try {
            setCreateLoading(true)
            const {
                data: { id },
            } = await api.post('/lists', values)
            form.resetFields()
            await fetch()
        } catch (error) {
            console.error(error)
            notification.error({ description: 'Failed to create product list' })
        } finally {
            setCreateLoading(false)
        }
        console.log(values)
    }

    return (
        <div className={'product-lists'}>
            <div className={c('header')}>
                <span className={c('header-title')}>Product Lists</span>
                <Form form={form} layout="inline" requiredMark={'optional'} onFinish={onFinish}>
                    <Form.Item
                        name="name"
                        label="Create a new product list"
                        rules={[
                            {
                                required: true,
                                message: 'Please enter the name of the list',
                            },
                            {
                                message: 'Make sure the list has at least 3 characters',
                                validator: (rule, val) => {
                                    const _val = val.trim()
                                    if (!_val) return Promise.reject(false)
                                    if (_val.length < 3) return Promise.reject(false)
                                    return Promise.resolve(true)
                                },
                            },
                        ]}>
                        <Input />
                    </Form.Item>

                    <Form.Item>
                        <Button type="primary" htmlType="submit" loading={createLoading}>
                            Create
                        </Button>
                    </Form.Item>
                </Form>
            </div>
            <Spin spinning={isLoading}>
                <div className={c('content')}>
                    {lists.map(list => {
                        return <ProductList key={list.id} list={list} onChange={onChange} />
                    })}
                </div>
            </Spin>
        </div>
    )
}

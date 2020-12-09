import './ChooseListModalInner.less'

import React, { useEffect, useState } from 'react'
import { Button, List, notification, Skeleton } from 'antd'
import { sleep } from '../utils'
import { HeartOutlined, HeartFilled } from '@ant-design/icons'

export const ChooseListModalInner = ({ product, onChooseList }) => {
    const [lists, setLists] = useState([])
    const [loading, setLoading] = useState(true)
    const [loadingList, setLoadingList] = useState(null)

    const getLists = async (product, showLoading = true) => {
        try {
            setLoading(showLoading)
            await sleep(1000)
            // backend call to get lists of user

            setLists([
                {
                    id: 1,
                    name: 'Electronics',
                    hasProduct: true,
                },

                {
                    id: 2,
                    name: 'Thanksgiving',
                    hasProduct: false,
                },
                {
                    id: 3,
                    name: 'Blackfriday',
                    hasProduct: true,
                },
            ])
        } catch (error) {
        } finally {
            setLoading(false)
        }
    }

    useEffect(() => {
        getLists(product, true)
    }, [])

    const removeProductFromList = async (product, list) => {
        try {
            setLoadingList(list)
            await sleep(1000) // actual code here
            notification.success({ description: 'Added product to list' })
        } catch (error) {
            notification.error({ description: 'Failed to remove product from list' })
        } finally {
            setLoadingList(null)
        }
    }

    const addProductToList = async (product, list) => {
        try {
            setLoadingList(list)
            await sleep(1000) // actual code here
            notification.success({ description: 'Added product to list' })
        } catch (error) {
            notification.error({ description: 'Failed to remove product from list' })
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
        await getLists(product, false)
    }

    if (loading) {
        return (
            <List size="large" itemLayout="horizontal">
                <List.Item key={1} extra={[<Button disabled type="ghost" icon={<HeartOutlined />} />]}>
                    <Skeleton paragraph={{ rows: 1 }} active title={false} />
                </List.Item>
                <List.Item key={2} extra={[<Button disabled type="ghost" icon={<HeartOutlined />} />]}>
                    <Skeleton paragraph={{ rows: 1 }} active title={false} />
                </List.Item>
                <List.Item key={3} extra={[<Button disabled type="ghost" icon={<HeartOutlined />} />]}>
                    <Skeleton paragraph={{ rows: 1 }} active title={false} />
                </List.Item>
            </List>
        )
    }

    return (
        <List loading={loading} size="large" itemLayout="horizontal">
            {lists.map((list, ix) => {
                return (
                    <List.Item
                        key={list.id ?? ix}
                        className={'choose-list-modal-list'}
                        actions={[
                            <Button
                                loading={list.id === loadingList?.id}
                                type="ghost"
                                icon={list.hasProduct ? <HeartFilled /> : <HeartOutlined />}
                                onClick={onListClick(list)}
                            />,
                        ]}>
                        {list.name ?? `Example list name ${ix}`}
                    </List.Item>
                )
            })}
        </List>
    )
}

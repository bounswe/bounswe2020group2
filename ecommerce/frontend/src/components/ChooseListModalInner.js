import './ChooseListModalInner.less'

import React, { useEffect, useState } from 'react'
import { List } from 'antd'
import { sleep } from '../utils'

export const ChooseListModalInner = ({ onChooseList }) => {
    const [lists, setLists] = useState([])
    const [loading, setLoading] = useState(true)

    useEffect(() => {
        async function fetch() {
            try {
                setLoading(true)
                await sleep(1000)

                setLists([
                    {
                        id: 1,
                        name: 'Electronics',
                    },

                    {
                        id: 2,
                        name: 'Thanksgiving',
                    },
                    {
                        id: 3,
                        name: 'Blackfriday',
                    },
                ])
            } catch (error) {
            } finally {
                setLoading(false)
            }
        }

        fetch()
    }, [])

    const onListClick = list => () => onChooseList(list)

    return (
        <List loading={loading} size="large">
            {lists.map((list, ix) => {
                return (
                    <List.Item className={'choose-list-modal-list'} onClick={onListClick(list)} key={list.id ?? ix}>
                        {list.name ?? `Example list name ${ix}`}
                    </List.Item>
                )
            })}
        </List>
    )
}

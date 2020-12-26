import { Button, List, Spin, notification } from 'antd'
import { sleep } from '../../utils'
import { useEffect, useState } from 'react'
import { AddressCard } from './AddressCard'
import { AddressModal } from './AddressModal'
import { useAppContext } from '../../context/AppContext'
import './AddressList.less'

export const AddressList = ({onSelect = () => {}}) => {
    const [addressList, setAddressList] = useState([])
    const { user } = useAppContext()

    const getAddressList = async userId => {
        let addresses = []
        try {
            setIsLoading(true)
            await sleep(2000)
            addresses = [
                {
                    id: 44313,
                    title: 'Home Address',
                    phone: {
                        countryCode: '+90',
                        number: '5555555555',
                    },
                    name: 'Özdeniz',
                    surname: 'Dolu',
                    zipCode: '34000',
                    address: 'React Mah. JS Sk. No: 42/1',
                    province: 'Besiktas',
                    city: 'Istanbul',
                    country: 'Turkey',
                },
                {
                    id: 12322,
                    title: 'Work Address',
                    phone: {
                        countryCode: '+90',
                        number: '544444444',
                    },
                    name: 'Özdeniz',
                    surname: 'Dolu',
                    zipCode: '35000',
                    address: 'Python Mah. Numpy Sk. No: 23/1',
                    province: 'Besiktas',
                    city: 'Istanbul',
                    country: 'Turkey',
                },
            ]
        } catch (error) {
            notification.warning({ message: 'There was an error with your request.' })
            console.error(error)
        } finally {
            setIsLoading(false)
            return addresses
        }
    }

    useEffect(() => {
        async function fetch() {
            const addresses = await getAddressList(user?.id)
            setAddressList(addresses)
            if(addresses.length > 0) {
                setSelectedAddress(addresses[0].id)
                onSelect(addresses[0].id)
            }
        }
        fetch()
    }, [user])

    const [selectedAddress, setSelectedAddress] = useState()
    const [addVisible, setAddVisible] = useState(false)
    const [isLoading, setIsLoading] = useState(false)

    const onAddressSelect = addressId => {
        setSelectedAddress(addressId)
        onSelect(addressId)
    }

    const onAddressInfoChange = async () => {
        const addresses = await getAddressList(user?.id)
        setAddressList(addresses)
    }

    return (
        <div className="addresslist-container">
            <div className="addresslist-title">
                Select an address
                <Button onClick={() => setAddVisible(true)} type="dashed">
                    Add a new address
                </Button>
                <AddressModal
                    mode="add"
                    onCancel={() => setAddVisible(false)}
                    onSuccess={() => {
                        setAddVisible(false)
                        onAddressInfoChange()
                    }}
                    visible={addVisible}
                />
            </div>
            <div className="addresslist-list">
                <Spin spinning={isLoading}>
                    <List
                        locale={{ emptyText: 'Add a new address!' }}
                        grid={{ gutter: 0 }}
                        dataSource={addressList}
                        renderItem={address => (
                            <List.Item key={address.id}>
                                <AddressCard
                                    address={address}
                                    onAddressInfoChange={onAddressInfoChange}
                                    selected={selectedAddress === address.id}
                                    onSelect={onAddressSelect}
                                />
                            </List.Item>
                        )}
                    />
                </Spin>
            </div>
        </div>
    )
}

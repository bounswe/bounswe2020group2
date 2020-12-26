import { Button, List, Spin, notification } from 'antd'
import { sleep } from '../../utils'
import { api } from '../../api'
import { useEffect, useState } from 'react'
import { AddressCard } from './AddressCard'
import { AddressModal } from './AddressModal'
import { useAppContext } from '../../context/AppContext'
import './AddressList.less'

export const AddressList = ({ onSelect = () => {} }) => {
    const [addressList, setAddressList] = useState([])
    const { user } = useAppContext()

    const fetchAddressList = async () => {
        setIsLoading(true)
        try {
            const {
                data: { status, addresses },
            } = await api.get(`/customer/${user.id}/addresses`)
            if (status.successful) {
                setAddressList(addresses)
                const defaultAddress = addresses[0]?.id
                setSelectedAddress(defaultAddress)
                onSelect(defaultAddress)
            } else {
                notification.warning({ message: status.message })
            }
        } catch (error) {
            notification.warning({ message: 'There was an error with your request.' })
            console.error(error)
        } finally {
            setIsLoading(false)
        }
    }

    useEffect(() => {
        fetchAddressList()
    }, [user])

    const [selectedAddress, setSelectedAddress] = useState()
    const [addVisible, setAddVisible] = useState(false)
    const [isLoading, setIsLoading] = useState(false)

    const onAddressSelect = addressId => {
        setSelectedAddress(addressId)
        onSelect(addressId)
    }

    const onAddressInfoChange = () => {
        fetchAddressList()
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

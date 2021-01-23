import React, { useEffect, useState } from 'react'
import { api } from '../api'
import { Result } from 'antd'
import './EmailVerification.less'
import { LoadingOutlined } from '@ant-design/icons'
import { useAppContext } from '../context/AppContext'

const VerificationMessage = ({ verificationState }) => {
    if (verificationState == 'Success') {
        return (
            <div className="verify-message">
                <Result status="success" title="Your account has been verified" />
            </div>
        )
    } else if (verificationState == 'Invalid') {
        return (
            <div className="verify-message">
                <Result status="warning" title="Failed to verify account" />
            </div>
        )
    } else if (verificationState == 'Expired') {
        return (
            <div className="verify-message">
                <Result status="warning" title="Your activitation link is expired" />
            </div>
        )
    } else if (verificationState == 'Verified') {
        return (
            <div className="verify-message">
                <Result status="warning" title="Your account has already been verified" />
            </div>
        )
    } else {
        return (
            <div className="verify-message">
                <Result icon={<LoadingOutlined />} title="Your account is being verified. Please wait..." />
            </div>
        )
    }
}

export const EmailVerification = props => {
    const { id } = props.match.params
    const [verificationState, setVerificationState] = useState('waiting')
    const { user, setUser } = useAppContext()

    useEffect(() => {
        async function fetch() {
            try {
                const {
                    data: {
                        status: { message, successful },
                    },
                } = await api.get(`/email-verify/${id}`)
                setVerificationState(message)
                if (successful) {
                    setUser({ ...user, is_verified: true })
                }
            } catch (error) {
                console.error(error)
                setVerificationState('Invalid')
            } finally {
            }
        }
        fetch()
    }, [])

    return <div className="verify-message-box">{<VerificationMessage verificationState={verificationState} />}</div>
}

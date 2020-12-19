import React, { useEffect, useState } from 'react'
import { api } from '../api'
import { Result, Button } from 'antd'
import { sleep } from '../utils'
import './EmailVerification.less'
import { LoadingOutlined } from '@ant-design/icons'

const VerificationMessage = ({ verificationState }) => {
    console.log(verificationState)
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

    useEffect(() => {
        async function fetch() {
            try {
                console.log('token: ', id)
                const { data } = await api.get(`/email-verify/${id}`)
                await sleep(1000)
                setVerificationState(data.message)
            } catch (error) {
                console.error('failed to verify account', error)
                setVerificationState('Invalid')
            } finally {
            }
        }
        fetch()
    }, [])

    return <div className="verify-message-box">{<VerificationMessage verificationState={verificationState} />}</div>
}

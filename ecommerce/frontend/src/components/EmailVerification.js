import React, { useEffect, useState } from 'react'
import { api } from '../api'
import { notification } from 'antd'
import { sleep } from '../utils'
import './EmailVerification.less'
import { CheckCircleTwoTone, CloseCircleTwoTone } from '@ant-design/icons'

const VerificationMessage = verificationState => {
    console.log(verificationState)
    if (verificationState == 'success') {
        return (
            <div className="verify-message">
                <p>Your account has been verified.</p>
                <CheckCircleTwoTone />
            </div>
        )
    } else if (verificationState == 'failure') {
        return (
            <div className="verify-message">
                <p>Failed to verify account</p>
                <CloseCircleTwoTone />
            </div>
        )
    } else {
        return (
            <div className="verify-message">
                <p>Your account is being verified. Please wait..</p>
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
                // const { data } = await api.post(`/email-verify/${id}`)
                await sleep(2000)
                setVerificationState('success')
            } catch (error) {
                console.error('failed to verify account', error)
            } finally {
            }
        }
        fetch()
    }, [])

    return <div className="verify-message-box">{VerificationMessage(verificationState)}</div>
}

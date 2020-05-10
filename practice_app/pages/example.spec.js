function good_sub(x, y) {
    return x - y
}

function bad_sub(x, y) {
    return y - x // reversed
}

// describe, test and expect functions are globals defined by Jest framework inside test files
describe('Subtract function', () => {
    test('2 - 2 = 0', () => {
        const a = 2
        const b = 2
        const expected = 0
        const output = bad_sub(a, b)

        // https://jestjs.io/docs/en/expect
        expect(output).toEqual(expected)
    })
    test('3 - 2 = 1', () => {
        // this should fail
        const a = 3
        const b = 2
        const expected = 1
        const output = bad_sub(a, b)
        expect(output).toEqual(expected)
    })
})

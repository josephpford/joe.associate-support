const apiUrl = `${window.API_URL}/quote`;

export async function findAll() {
    const response = await fetch(apiUrl);
    if (response.status === 200) {
        return response.json();
    } else {
        return Promise.reject([`Bad status: ${response.status}.`]);
    }
}

export async function findById(id) {
    const response = await fetch(`${apiUrl}/${id}`);
    if (response.status === 200) {
        return response.json();
    } else {
        return Promise.reject([`Bad status: ${response.status}.`]);
    }
}

async function add(quote) {
    const init = {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
        },
        body: JSON.stringify(quote)
    };
    const response = await fetch(apiUrl, init);
    if (response.status === 201) {
        return response.json();
    } else if (response.status === 400) {
        return Promise.reject(await response.json());
    }
    return Promise.reject(["Could not add quote."]);
}

async function update(quote) {
    const init = {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(quote)
    };
    const response = await fetch(apiUrl, init);
    if (response.status === 204) {
        return Promise.resolve();
    } else if (response.status === 400) {
        return Promise.reject(await response.json());
    }
    return Promise.reject([`Could not update quote. Status: ${response.status}`]);
}

export async function save(quote) {
    return quote.quoteId > 0 ? update(quote) : add(quote);
}

export async function deleteById(quoteId) {
    const response = await fetch(`${apiUrl}/${quoteId}`, { method: "DELETE" });
    if (response.status === 204) {
        return Promise.resolve();
    }
    return Promise.reject(["Could not delete game."]);
}
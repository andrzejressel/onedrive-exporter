package com.andrzejressel.onedrivecollector.onedrive

import com.andrzejressel.onedrivecollector.onedrive.model.MicrosoftError
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper
import javax.annotation.Priority
import javax.ws.rs.core.MultivaluedMap
import javax.ws.rs.core.Response

@Priority(4000)
class MicrosoftExceptionMapper: ResponseExceptionMapper<MicrosoftApiException> {
    override fun toThrowable(response: Response): MicrosoftApiException {
        val error = response.readEntity(MicrosoftError::class.java)
        return MicrosoftApiException(error)
    }

    override fun handles(status: Int, headers: MultivaluedMap<String, Any>?): Boolean {
        return status == 400
    }

}
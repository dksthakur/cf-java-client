/*
 * Copyright 2009-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.cloudfoundry.client.lib;

import org.cloudfoundry.client.lib.archive.ApplicationArchive;
import org.cloudfoundry.client.lib.domain.ApplicationLog;
import org.cloudfoundry.client.lib.domain.ApplicationStats;
import org.cloudfoundry.client.lib.domain.CloudApplication;
import org.cloudfoundry.client.lib.domain.CloudDomain;
import org.cloudfoundry.client.lib.domain.CloudInfo;
import org.cloudfoundry.client.lib.domain.CloudOrganization;
import org.cloudfoundry.client.lib.domain.CloudQuota;
import org.cloudfoundry.client.lib.domain.CloudRoute;
import org.cloudfoundry.client.lib.domain.CloudService;
import org.cloudfoundry.client.lib.domain.CloudServiceBroker;
import org.cloudfoundry.client.lib.domain.CloudServiceOffering;
import org.cloudfoundry.client.lib.domain.CloudSpace;
import org.cloudfoundry.client.lib.domain.CloudStack;
import org.cloudfoundry.client.lib.domain.CrashesInfo;
import org.cloudfoundry.client.lib.domain.InstancesInfo;
import org.cloudfoundry.client.lib.domain.Staging;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * The interface defining operations making up the Cloud Foundry Java client's API.
 *
 * @author Ramnivas Laddad
 * @author A.B.Srinivasan
 * @author Jennifer Hickey
 * @author Dave Syer
 * @author Thomas Risberg
 * @author Alexander Orlov
 */
public interface CloudFoundryOperations {

    /**
     * Add a private domain in the current organization.
     *
     * @param domainName the domain to add
     */
    void addDomain(String domainName);

    /**
     * Register a new route to the a domain.
     *
     * @param host       the host of the route to register
     * @param domainName the domain of the route to register
     */
    void addRoute(String host, String domainName);

    /**
     * Associate (provision) a service with an application.
     *
     * @param appName     the application name
     * @param serviceName the service name
     */
    void bindService(String appName, String serviceName);

    /**
     * Create application.
     *
     * @param appName      application name
     * @param staging      staging info
     * @param memory       memory to use in MB
     * @param uris         list of URIs for the app
     * @param serviceNames list of service names to bind to app
     */
    void createApplication(String appName, Staging staging, Integer memory, List<String> uris,
                           List<String> serviceNames);

    /**
     * Create application.
     *
     * @param appName      application name
     * @param staging      staging info
     * @param disk         disk quota to use in MB
     * @param memory       memory to use in MB
     * @param uris         list of URIs for the app
     * @param serviceNames list of service names to bind to app
     */
    public void createApplication(String appName, Staging staging, Integer disk, Integer memory, List<String> uris,
                                  List<String> serviceNames);

    /**
     * Create quota
     *
     * @param quota
     */
    void createQuota(CloudQuota quota);

    /**
     * Create a service.
     *
     * @param service cloud service info
     */
    void createService(CloudService service);

    /**
     * Create a service broker.
     *
     * @param serviceBroker cloud service broker info
     */
    void createServiceBroker(CloudServiceBroker serviceBroker);

    /**
     * Create a space with the specified name
     *
     * @param spaceName
     */
    void createSpace(String spaceName);

    /**
     * Create a user-provided service.
     *
     * @param service     cloud service info
     * @param credentials the user-provided service credentials
     */
    void createUserProvidedService(CloudService service, Map<String, Object> credentials);

    /**
     * Create a user-provided service for logging.
     *
     * @param service        cloud service info
     * @param credentials    the user-provided service credentials
     * @param syslogDrainUrl for a logging service
     */
    void createUserProvidedService(CloudService service, Map<String, Object> credentials, String syslogDrainUrl);

    /**
     * Debug application.
     *
     * @param appName name of application
     * @param mode    debug mode info
     */
    void debugApplication(String appName, CloudApplication.DebugMode mode);

    /**
     * Delete all applications.
     */
    void deleteAllApplications();

    /**
     * Delete all services.
     */
    void deleteAllServices();

    /**
     * Delete application.
     *
     * @param appName name of application
     */
    void deleteApplication(String appName);

    /**
     * Delete a private domain in the current organization.
     *
     * @param domainName the domain to delete
     */
    void deleteDomain(String domainName);

    /**
     * Delete routes that do not have any application which is assigned to them.
     */
    List<CloudRoute> deleteOrphanedRoutes();

    /**
     * Delete quota by name
     *
     * @param quotaName
     */
    void deleteQuota(String quotaName);

    /**
     * Delete a registered route from the space of the current session.
     *
     * @param host       the host of the route to delete
     * @param domainName the domain of the route to delete
     */
    void deleteRoute(String host, String domainName);

    /**
     * Delete cloud service.
     *
     * @param service name of service
     */
    void deleteService(String service);

    /**
     * Delete a service broker.
     *
     * @param name the service broker name
     */
    void deleteServiceBroker(String name);

    /**
     * Delete a space with the specified name
     *
     * @param spaceName name of the space
     */
    void deleteSpace(String spaceName);

    /**
     * Get cloud application with the specified name.
     *
     * @param appName name of the app
     * @return the cloud application
     */
    CloudApplication getApplication(String appName);

    /**
     * Get application instances info for application.
     *
     * @param appName name of application.
     * @return instances info
     */
    InstancesInfo getApplicationInstances(String appName);

    /**
     * Get application instances info for application.
     *
     * @param app the application.
     * @return instances info
     */
    InstancesInfo getApplicationInstances(CloudApplication app);

    /**
     * Get application stats for the app with the specified name.
     *
     * @param appName name of the app
     * @return the cloud application stats
     */
    ApplicationStats getApplicationStats(String appName);

    /**
     * Get all cloud applications.
     *
     * @return list of cloud applications
     */
    List<CloudApplication> getApplications();

    /**
     * Get the URL used for the cloud controller.
     *
     * @return the cloud controller URL
     */
    URL getCloudControllerUrl();

    /**
     * Get CloudInfo for the current cloud.
     *
     * @return CloudInfo object containing the cloud info
     */
    CloudInfo getCloudInfo();

    /**
     * Get logs from most recent crash of the deployed application. The logs will be returned in a Map keyed by the
     * path
     * of the log file (logs/stderr.log, logs/stdout.log).
     *
     * @param appName name of the application
     * @return a Map containing the logs. The logs will be returned with the path to the log file used as the key and
     * the full content of the log file will be returned as a String value for the corresponding key.
     * @deprecated Use {@link #streamLogs(String, ApplicationLogListener)} or {@link #getRecentLogs(String)}
     */
    Map<String, String> getCrashLogs(String appName);

    /**
     * Get crashes info for application.
     *
     * @param appName name of application
     * @return crashes info
     */
    CrashesInfo getCrashes(String appName);

    /**
     * Gets the default domain for the current org, which is the first shared domain.
     *
     * @return the default domain
     */
    CloudDomain getDefaultDomain();

    /**
     * Get list of all domain shared and private domains.
     *
     * @return list of domains
     */
    List<CloudDomain> getDomains();

    /**
     * Get list of all domain registered for the current organization.
     *
     * @return list of domains
     */
    List<CloudDomain> getDomainsForOrg();

    /**
     * Get file from the deployed application.
     *
     * @param appName       name of the application
     * @param instanceIndex instance index
     * @param filePath      path to the file
     * @return the contents of the file
     */
    String getFile(String appName, int instanceIndex, String filePath);

    /**
     * Get a the content, starting at a specific position, of a file from the deployed application.
     *
     * @param appName       name of the application
     * @param instanceIndex instance index
     * @param filePath      path to the file
     * @param startPosition the starting position of the file contents (inclusive)
     * @return the contents of the file
     */
    String getFile(String appName, int instanceIndex, String filePath, int startPosition);

    /**
     * Get a range of content of a file from the deployed application. The range begins at the specified startPosition
     * and extends to the character at endPosition - 1.
     *
     * @param appName       name of the application
     * @param instanceIndex instance index
     * @param filePath      path to the file
     * @param startPosition the starting position of the file contents (inclusive)
     * @param endPosition   the ending position of the file contents (exclusive)
     * @return the contents of the file
     */
    String getFile(String appName, int instanceIndex, String filePath, int startPosition, int endPosition);

    /**
     * Get a the last bytes, with length as specified, of content of a file from the deployed application.
     *
     * @param appName       name of the application
     * @param instanceIndex instance index
     * @param filePath      path to the file
     * @param length        the length of the file contents to retrieve
     * @return the contents of the file
     */
    String getFileTail(String appName, int instanceIndex, String filePath, int length);

    /**
     * Get logs from the deployed application. The logs will be returned in a Map keyed by the path of the log file
     * (logs/stderr.log, logs/stdout.log).
     *
     * @param appName name of the application
     * @return a Map containing the logs. The logs will be returned with the path to the log file used as the key and
     * the full content of the log file will be returned as a String value for the corresponding key.
     * @deprecated Use {@link #streamLogs(String, ApplicationLogListener)} or {@link #getRecentLogs(String)}
     */
    Map<String, String> getLogs(String appName);

    /**
     * Get list of CloudOrganizations for the current cloud.
     *
     * @return List of CloudOrganizations objects containing the organization info
     */
    List<CloudOrganization> getOrganizations();

    /**
     * Get list of all private domains.
     *
     * @return list of private domains
     */
    List<CloudDomain> getPrivateDomains();

    /**
     * Get quota by name
     *
     * @param quotaName
     * @param required
     * @return CloudQuota instance
     */
    CloudQuota getQuotaByName(String quotaName, boolean required);

    /**
     * Get quota definitions
     *
     * @return List<CloudQuota>
     */
    List<CloudQuota> getQuotas();

    /**
     * Stream recent log entries.
     *
     * Stream logs that were recently produced for an app.
     *
     * @param appName the name of the application
     * @return the list of recent log entries
     */
    List<ApplicationLog> getRecentLogs(String appName);

    /**
     * Get the info for all routes for a domain.
     *
     * @param domainName the domain the routes belong to
     * @return list of routes
     */
    List<CloudRoute> getRoutes(String domainName);

    /**
     * Get cloud service.
     *
     * @param service name of service
     * @return the cloud service info
     */
    CloudService getService(String service);

    /**
     * Get a service broker.
     *
     * @param name the service broker name
     * @return the service broker
     */
    CloudServiceBroker getServiceBroker(String name);

    /**
     * Get all service brokers.
     *
     * @return
     */
    List<CloudServiceBroker> getServiceBrokers();

    /**
     * Get all service offerings.
     *
     * @return list of service offerings
     */
    List<CloudServiceOffering> getServiceOfferings();

    /**
     * Get list of cloud services.
     *
     * @return list of cloud services
     */
    List<CloudService> getServices();

    /**
     * Get list of all shared domains.
     *
     * @return list of shared domains
     */
    List<CloudDomain> getSharedDomains();

    /**
     * Get space name with the specified name.
     *
     * @param spaceName name of the space
     * @return the cloud space
     */
    CloudSpace getSpace(String spaceName);

    /**
     * Get list of CloudSpaces for the current cloud.
     *
     * @return List of CloudSpace objects containing the space info
     */
    List<CloudSpace> getSpaces();

    /**
     * Get a stack by name.
     *
     * @param name the name of the stack to get
     * @return the stack, or null if not found
     */
    CloudStack getStack(String name);

    /**
     * Get the list of stacks available for staging applications.
     *
     * @return the list of available stacks
     */
    List<CloudStack> getStacks();

    /**
     * Get the staging log while an application is starting. A null value indicates that no further checks for staging
     * logs should occur as staging logs are no longer available.
     *
     * @param info   starting information containing staging log file URL. Obtained after starting an application.
     * @param offset starting position from where content should be retrieved.
     * @return portion of the staging log content starting from the offset. It may contain multiple lines. Returns null
     * if no further content is available.
     */
    String getStagingLogs(StartingInfo info, int offset);

    /**
     * Login using the credentials already set for the client.
     *
     * @return authentication token
     */
    OAuth2AccessToken login();

    /**
     * Logout closing the current session.
     */
    void logout();

    /**
     * Register new user account with the provided credentials.
     *
     * @param email    the email account
     * @param password the password
     */
    void register(String email, String password);

    /**
     * Register a new RestLogCallback
     *
     * @param callBack the callback to be registered
     */
    void registerRestLogListener(RestLogCallback callBack);

    /**
     * Delete a private domain in the current organization.
     *
     * @param domainName the domain to remove
     * @deprecated alias for {@link #deleteDomain}
     */
    void removeDomain(String domainName);

    /**
     * Rename an application.
     *
     * @param appName the current name
     * @param newName the new name
     */
    void rename(String appName, String newName);

    /**
     * Restart application.
     *
     * @param appName name of application
     */
    StartingInfo restartApplication(String appName);

    /**
     * Set quota to organization
     *
     * @param orgName
     * @param quotaName
     */
    void setQuotaToOrg(String orgName, String quotaName);

    /**
     * Override the default REST response error handler with a custom error handler.
     *
     * @param errorHandler
     */
    void setResponseErrorHandler(ResponseErrorHandler errorHandler);

    /**
     * Start application. May return starting info if the response obtained after the start request contains headers
     * . If
     * the response does not contain headers, null is returned instead.
     *
     * @param appName name of application
     * @return Starting info containing response headers, if headers are present in the response. If there are no
     * headers, return null.
     */
    StartingInfo startApplication(String appName);

    /**
     * Stop application.
     *
     * @param appName name of application
     */
    void stopApplication(String appName);

    /**
     * Stream application logs produced <em>after</em> this method is called.
     *
     * This method has 'tail'-like behavior. Every time there is a new log entry, it notifies the listener.
     *
     * @param appName  the name of the application
     * @param listener listener object to be notified
     * @return token than can be used to cancel listening for logs
     */
    StreamingLogToken streamLogs(String appName, ApplicationLogListener listener);

    /**
     * Un-register a RestLogCallback
     *
     * @param callBack the callback to be un-registered
     */
    void unRegisterRestLogListener(RestLogCallback callBack);

    /**
     * Un-associate (unprovision) a service from an application.
     *
     * @param appName     the application name
     * @param serviceName the service name
     */
    void unbindService(String appName, String serviceName);

    /**
     * Unregister and log out the currently logged in user
     */
    void unregister();

    /**
     * Update application disk quota.
     *
     * @param appName name of application
     * @param disk    new disk setting in MB
     */
    void updateApplicationDiskQuota(String appName, int disk);

    /**
     * Update application env using a map where the key specifies the name of the environment variable and the value the
     * value of the environment variable..
     *
     * @param appName name of application
     * @param env     map of environment settings
     */
    void updateApplicationEnv(String appName, Map<String, String> env);

    /**
     * Update application env using a list of strings each with one environment setting.
     *
     * @param appName name of application
     * @param env     list of environment settings
     */
    void updateApplicationEnv(String appName, List<String> env);

    /**
     * Update application instances.
     *
     * @param appName   name of application
     * @param instances number of instances to use
     */
    void updateApplicationInstances(String appName, int instances);

    /**
     * Update application memory.
     *
     * @param appName name of application
     * @param memory  new memory setting in MB
     */
    void updateApplicationMemory(String appName, int memory);

    /**
     * Update application services.
     *
     * @param appName  name of appplication
     * @param services list of services that should be bound to app
     */
    void updateApplicationServices(String appName, List<String> services);

    /**
     * Update application staging information.
     *
     * @param appName name of appplication
     * @param staging staging information for the app
     */
    void updateApplicationStaging(String appName, Staging staging);

    /**
     * Update application URIs.
     *
     * @param appName name of application
     * @param uris    list of URIs the app should use
     */
    void updateApplicationUris(String appName, List<String> uris);

    /**
     * Update the password for the logged in user.
     *
     * @param newPassword the new password
     */
    void updatePassword(String newPassword);

    /**
     * Update the password for the logged in user using the username/old_password provided in the credentials.
     *
     * @param credentials current credentials
     * @param newPassword the new password
     */
    void updatePassword(CloudCredentials credentials, String newPassword);

    /**
     * Update Quota definition
     *
     * @param quota
     * @param name
     */
    void updateQuota(CloudQuota quota, String name);

    /**
     * Update a service broker (unchanged forces catalog refresh).
     *
     * @param serviceBroker cloud service broker info
     */
    void updateServiceBroker(CloudServiceBroker serviceBroker);

    /**
     * Service plans are private by default when a service broker's catalog is fetched/updated. This method will update
     * the visibility of all plans for a broker to either public or private.
     *
     * @param name       the service broker name
     * @param visibility true for public, false for private
     */
    void updateServicePlanVisibilityForBroker(String name, boolean visibility);

    /**
     * Upload an application to Cloud Foundry.
     *
     * @param appName application name
     * @param file    path to the application archive or folder
     * @throws java.io.IOException
     */
    void uploadApplication(String appName, String file) throws IOException;

    /**
     * Upload an application to Cloud Foundry.
     *
     * @param appName the application name
     * @param file    the application archive or folder
     * @throws java.io.IOException
     */
    void uploadApplication(String appName, File file) throws IOException;

    /**
     * Upload an application to Cloud Foundry.
     *
     * @param appName  the application name
     * @param file     the application archive
     * @param callback a callback interface used to provide progress information or <tt>null</tt>
     * @throws java.io.IOException
     */
    void uploadApplication(String appName, File file, UploadStatusCallback callback) throws IOException;

    /**
     * Upload an application to Cloud Foundry.
     *
     * This form of <tt>uploadApplication</tt> will read the passed <tt>InputStream</tt> and copy the contents to a
     * temporary file for upload.
     *
     * @param appName     the application name
     * @param fileName    the logical name of the application file
     * @param inputStream the InputStream to read from
     * @throws java.io.IOException
     */
    void uploadApplication(String appName, String fileName, InputStream inputStream) throws IOException;

    /**
     * Upload an application to Cloud Foundry.
     *
     * This form of <tt>uploadApplication</tt> will read the passed <tt>InputStream</tt> and copy the contents to a
     * temporary file for upload.
     *
     * @param appName     the application name
     * @param fileName    the logical name of the application file
     * @param inputStream the InputStream to read from
     * @param callback    a callback interface used to provide progress information or <tt>null</tt>
     * @throws java.io.IOException
     */
    void uploadApplication(String appName, String fileName, InputStream inputStream, UploadStatusCallback callback)
            throws IOException;

    /**
     * Upload an application to Cloud Foundry.
     *
     * @param appName the application name
     * @param archive the application archive
     * @throws java.io.IOException
     */
    void uploadApplication(String appName, ApplicationArchive archive) throws IOException;

    /**
     * Upload an application to Cloud Foundry.
     *
     * @param appName  the application name
     * @param archive  the application archive
     * @param callback a callback interface used to provide progress information or <tt>null</tt>
     * @throws java.io.IOException
     */
    void uploadApplication(String appName, ApplicationArchive archive, UploadStatusCallback callback) throws
            IOException;
}
using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Communication.Dispatcher
{
    class DispatcherBase
    {
        /// <summary>
        /// Dummymethode für Funktionszuweisungen von ServerResponses
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="methodName"></param>
        public void CallMethodFromString<T>(String methodName) where T : new()
        {
            T instance = new T();
            MethodInfo method = typeof(T).GetMethod(methodName);
            method.Invoke(instance, null);
        }
    }
}
